import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FrameLightComponent } from './frame-light.component';

describe('FrameLightComponent', () => {
  let component: FrameLightComponent;
  let fixture: ComponentFixture<FrameLightComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FrameLightComponent]
    });
    fixture = TestBed.createComponent(FrameLightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
