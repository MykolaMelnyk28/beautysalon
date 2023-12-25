import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewServiceDialogComponent } from './view-service-dialog.component';

describe('ViewServiceDialogComponent', () => {
  let component: ViewServiceDialogComponent;
  let fixture: ComponentFixture<ViewServiceDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewServiceDialogComponent]
    });
    fixture = TestBed.createComponent(ViewServiceDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
