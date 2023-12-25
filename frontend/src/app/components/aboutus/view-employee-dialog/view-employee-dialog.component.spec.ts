import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewEmployeeDialogComponent } from './view-employee-dialog.component';

describe('ViewEmployeeDialogComponent', () => {
  let component: ViewEmployeeDialogComponent;
  let fixture: ComponentFixture<ViewEmployeeDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewEmployeeDialogComponent]
    });
    fixture = TestBed.createComponent(ViewEmployeeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
