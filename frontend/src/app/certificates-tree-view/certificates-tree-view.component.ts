import { Component, OnInit } from '@angular/core';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material/tree';
import { TreeNode } from '../model/TreeNode.model';
import { CertificateService } from '../services/certificate.service';
import { MatSnackBar, MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';


@Component({
  selector: 'app-certificates-tree-view',
  templateUrl: './certificates-tree-view.component.html',
  styleUrls: ['./certificates-tree-view.component.scss']
})
export class CertificatesTreeViewComponent implements OnInit {

  treeControl = new NestedTreeControl<TreeNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<TreeNode>();

  constructor(private certificateService: CertificateService,
              private snackBar: MatSnackBar,
              private router: Router,
              public dialog: MatDialog,
              ) {}

  ngOnInit() {
    this.getTree();
  }

  getTree() {
    this.certificateService.getTree().subscribe(
      (response => {
        console.log(response);
        this.dataSource.data = response;
      }), (error => {
        console.log(error);
        this.snackBar.open('Error while getting tree data');
      })
    );
  }

  confirmRevocation(serialNumber: string, commonName: string) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
     data: 'Are you sure you want to revoke certificate  ' + commonName + '?'
    });

    dialogRef.afterClosed().subscribe(result => {
      if ( result === true ) {
        this.revoke(serialNumber);
       }

    });

  }

  revoke(serialNumber: string) {

    this.certificateService.revoke(serialNumber).subscribe(
      (response => {
        console.log(response);
        this.snackBar.open('Revoked');
      }), (error => {
        console.log(error);
        this.snackBar.open('Revoked');
      })
    );
  }
  hasChild = (_: number, node: TreeNode) => !!node.children && node.children.length > 0;

}
