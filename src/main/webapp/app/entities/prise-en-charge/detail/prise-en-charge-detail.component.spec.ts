import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PriseEnChargeDetailComponent } from './prise-en-charge-detail.component';

describe('PriseEnCharge Management Detail Component', () => {
  let comp: PriseEnChargeDetailComponent;
  let fixture: ComponentFixture<PriseEnChargeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PriseEnChargeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PriseEnChargeDetailComponent,
              resolve: { priseEnCharge: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PriseEnChargeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PriseEnChargeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load priseEnCharge on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PriseEnChargeDetailComponent);

      // THEN
      expect(instance.priseEnCharge()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
