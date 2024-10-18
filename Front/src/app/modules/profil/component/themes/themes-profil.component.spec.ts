import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThemesProfilComponent } from './themes-profil.component';

describe('ThemesComponent', () => {
  let component: ThemesProfilComponent;
  let fixture: ComponentFixture<ThemesProfilComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ThemesProfilComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ThemesProfilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
