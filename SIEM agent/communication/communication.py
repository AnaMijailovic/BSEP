import requests
import OpenSSL

'''
Defence First 2_1589395655897.p12 - sadrzi sertifikat kome server ne veruje; sertifikat i kljuc su u 
not_trusted.crt i not_trusted.key

siem_agent_1589306569889.p12 - sadrzi sertifikat kome server veruje; sertifikat i kljuc su u 
agent.crt i agent-key.key

pki.pem - sertifikat pki servera
centar.pem - sertifikat siem centra

'''

SIEM_PORT = 8082
PKI_PORT = 8081

AGENT_CERT = "../certificates/agent.crt"
AGENT_KEY = "../certificates/agent-key.key"
AGENT_CERT_NOT_TRUSTED = "../certificates/not_trusted.crt"
AGENT_KEY_NOT_TRUSTED = "../certificates/not_trusted.key"
SIEM_CERT = "../certificates/centar.pem"
PKI_CERT = "../certificates/pki.pem"
PASSWORD = "!!!changethis!!!"


# not used
def get_private_key(p12_file_path, password):

    with open(p12_file_path, 'rb') as file:
        p12 = OpenSSL.crypto.load_pkcs12(file.read(), password)

    file_type = OpenSSL.crypto.FILETYPE_PEM
    return OpenSSL.crypto.dump_privatekey(file_type, p12.get_privatekey())


def get_serial_number(path):

    """

    Get serial number of the certificate

    :param  path: path to the certificate pem file
    :return serial number of the certificate

    """

    with open(path, 'rb') as file:
        certificate = OpenSSL.crypto.load_certificate(OpenSSL.crypto.FILETYPE_PEM, file.read())

    return certificate.get_serial_number()


def generate_crt_key_files(p12_file_path, password, cert_path, private_key_path):
    """
      Write the key and certificate in separate pem files

    """
    with open(p12_file_path, 'rb') as file:
        p12 = OpenSSL.crypto.load_pkcs12(file.read(), password)

    file_type = OpenSSL.crypto.FILETYPE_PEM

    # write private key
    with open(private_key_path, 'wb') as file:
        file.write(OpenSSL.crypto.dump_privatekey(file_type, p12.get_privatekey()))
        file.flush()

    # write certificate
    with open(cert_path, 'wb') as file:
        file.write(OpenSSL.crypto.dump_certificate(file_type, p12.get_certificate()))
        file.flush()


def generate_pem(p12_file_path, password, pem_path):
    """
      Write the certificate in pem file

    """
    with open(p12_file_path, 'rb') as file:
        p12 = OpenSSL.crypto.load_pkcs12(file.read(), password)

    type_ = OpenSSL.crypto.FILETYPE_PEM

    with open(pem_path, 'wb') as f:
        f.write(OpenSSL.crypto.dump_certificate(type_, p12.get_certificate()))
        f.flush()


def check_if_revoked(certificate_path):

    """

    Send a request to the PKI to check if the certificate is revoked

    :param  certificate_path: path to the certificate pem file
    :return True if certificate is revoked, False otherwise

    """
    serial_number = get_serial_number(certificate_path)

    is_revoked = requests.get("https://localhost:8081/pki/certificates/" + str(serial_number) +
                              "/isRevoked", verify=PKI_CERT, cert=(AGENT_CERT, AGENT_KEY, PASSWORD))

    if is_revoked.text == "true":
        print("Server certificate is revoked")
        return True

    return False


def get(url, cert_path=AGENT_CERT, key_path=AGENT_KEY, verify_path=SIEM_CERT):

    """
    Send get request

    :param url: the url to which the request is sent
    :param cert_path: the path to the agent's certificate
    :param key_path: the path to the agent's private key
    :param verify_path: the path to the server's certificate

    """

    if check_if_revoked(verify_path):
        return

    try:
        response = requests.get(url, verify=verify_path, cert=(cert_path, key_path, PASSWORD))
        print('Status code: ' + str(response.status_code))
        print('Response text: ' + response.text)
    except requests.exceptions.SSLError:
        print("ERROR: Certificate verify failed")


def post(url, post_data="", cert_path=AGENT_CERT, key_path=AGENT_KEY, verify_path=SIEM_CERT):

    """
      Send post request - sends random message for now, just to check https

      :param url: the url to which the request is sent
      :param post_data:
      :param cert_path: the path to the agent's certificate
      :param key_path: the path to the agent's private key
      :param verify_path: the path to the server's certificate

      """

    if check_if_revoked(verify_path):
        return

    if post_data == "":
        post_data = "This is the message from SIEM agent :)"
    headers = {'Content-type': 'text/plain'}

    try:
        response = requests.post(url, data=post_data, headers=headers, cert=(cert_path, key_path), verify=verify_path)
        print('Status code: ' + str(response.status_code))
        print('Response text: ' + response.text)
    except requests.exceptions.SSLError:
        print("ERROR: Certificate verify failed")


if __name__ == "__main__":
    print("SIEM Agent started")

    # generate_crt_key_files("siem_agent_1589306569889.p12", PASSWORD, AGENT_CERT, AGENT_KEY)

    # Izbacuje vorninge posto nasi sertifikati nemaju subjectAltName ...
    print('\nTesting drools from siem with trusted certificate ...')
    get('https://localhost:8082/siem/demo/item', AGENT_CERT, AGENT_KEY, SIEM_CERT)

    print('\nGetting data from siem with trusted certificate ...')
    get('https://localhost:8082/siem/logs', AGENT_CERT, AGENT_KEY, SIEM_CERT)

    print('\nGetting data from siem with not trusted certificate ...')
    get('https://localhost:8082/siem/demo', AGENT_CERT_NOT_TRUSTED, AGENT_KEY_NOT_TRUSTED, SIEM_CERT)

    print('\nPosting data to siem with trusted certificate ...')
    post('https://localhost:8082/siem/demo', "", AGENT_CERT, AGENT_KEY, SIEM_CERT)

    print('\nPosting data to siem with with not trusted certificate ...')
    post('https://localhost:8082/siem/demo', "", AGENT_CERT_NOT_TRUSTED, AGENT_KEY_NOT_TRUSTED, SIEM_CERT)
