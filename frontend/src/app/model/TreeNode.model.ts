export interface TreeNode {
    serialNumber: string;
    type: string;
    commonName: string;
    revoked: boolean;
    children: TreeNode[];
  }
