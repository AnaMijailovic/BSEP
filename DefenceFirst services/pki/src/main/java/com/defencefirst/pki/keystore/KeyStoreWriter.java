package com.defencefirst.pki.keystore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defencefirst.pki.config.KeyStoreConfig;

@Service
public class KeyStoreWriter {

	private static final Logger LOGGER = LoggerFactory.getLogger(KeyStoreWriter.class);
	private static final String LOG_EXCEPTION_MSG = "exception";

	@Autowired
	private KeyStoreConfig keyStoreConfig;

	private KeyStore keyStore;

	@PostConstruct()
	public void init() {
		try {
			Security.addProvider(new BouncyCastleProvider());
			keyStore = KeyStore.getInstance(keyStoreConfig.getType(), BouncyCastleProvider.PROVIDER_NAME);
		} catch (KeyStoreException | NoSuchProviderException e) {
			LOGGER.error(LOG_EXCEPTION_MSG, e);
		}
	}

	public void loadKeyStore(String fileName, char[] password)
			throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {

		// open existing keystore
		if (fileName != null) {
			keyStore.load(new FileInputStream(fileName), password);
		} else {
			// create new keystore
			keyStore.load(null, password);
		}
	}

	public void loadOrCreateKeyStore(String fileName, char[] password) {
		try {
			loadKeyStore(fileName, keyStoreConfig.getPassword().toCharArray());
		} catch (Exception e) {
			try {
				loadKeyStore(null, keyStoreConfig.getPassword().toCharArray());
			} catch (NoSuchAlgorithmException | CertificateException | IOException e1) {
				LOGGER.error(LOG_EXCEPTION_MSG, e);
			}
		}
	}

	public void saveKeyStore(String fileName, char[] password) {
		try {
			keyStore.store(new FileOutputStream(fileName), password);
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			LOGGER.error(LOG_EXCEPTION_MSG, e);
		}
	}

	public void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate) {
		try {
			keyStore.setKeyEntry(alias, privateKey, password, new Certificate[] { certificate });
		} catch (KeyStoreException e) {
			LOGGER.error(LOG_EXCEPTION_MSG, e);
		}
	}

}
