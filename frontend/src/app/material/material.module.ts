import { NgModule } from '@angular/core';

import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {A11yModule} from '@angular/cdk/a11y';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {PortalModule} from '@angular/cdk/portal';
import {ScrollingModule} from '@angular/cdk/scrolling';
import {CdkStepperModule} from '@angular/cdk/stepper';
import {CdkTableModule} from '@angular/cdk/table';
import {CdkTreeModule} from '@angular/cdk/tree';
import {MatBadgeModule} from '@angular/material/badge';
import {MatBottomSheetModule} from '@angular/material/bottom-sheet';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatChipsModule} from '@angular/material/chips';
import {MatStepperModule} from '@angular/material/stepper';
import {MatDividerModule} from '@angular/material/divider';

import {
  MatIconModule,
  MatSnackBarModule,
  MatButtonModule,
  MatCardModule,
  MatMenuModule,
  MatSidenavModule,
  MatFormFieldModule,
  MatInputModule,
  MatTooltipModule,
  MatToolbarModule,
  MatSliderModule,
  MatGridListModule,
  MatExpansionModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatSelectModule,
  MatListModule,
  MatCheckboxModule,
  MatTabsModule,
  MatDialogModule,
  MatTableModule,
  MatSlideToggleModule,
  MatPaginatorModule,
  MatAutocompleteModule,
  MatSortModule,
  MatRadioModule,
  MAT_SNACK_BAR_DEFAULT_OPTIONS,
  MatTreeModule
} from '@angular/material';

const MaterialComponents = [
  MatButtonModule,
  MatCheckboxModule,
  MatInputModule,
  MatIconModule,
  MatToolbarModule,
  MatMenuModule,
  MatTableModule,
  MatSlideToggleModule,
  MatCardModule,
  MatTooltipModule,
  MatPaginatorModule,
  MatSelectModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatAutocompleteModule,
  MatDialogModule,
  MatListModule,
  MatTabsModule,
  MatSnackBarModule,
  MatSidenavModule,
  MatFormFieldModule,
  MatSliderModule,
  MatGridListModule,
  MatExpansionModule,
  MatSortModule,
  MatRadioModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  A11yModule,
  DragDropModule,
  PortalModule,
  ScrollingModule,
  CdkStepperModule,
  CdkTableModule,
  CdkTreeModule,
  MatBadgeModule,
  MatBottomSheetModule,
  MatButtonToggleModule,
  MatChipsModule,
  MatStepperModule,
  MatDividerModule,
  MatTreeModule

];


@NgModule({
  imports: [MaterialComponents],
  exports: [MaterialComponents],
  providers: [{ provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: { duration: 3800, panelClass: ['snackBar'] } }]
})
export class MaterialModule { }
