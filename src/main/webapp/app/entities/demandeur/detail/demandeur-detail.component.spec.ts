import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DemandeurDetailComponent } from './demandeur-detail.component';

describe('Demandeur Management Detail Component', () => {
  let comp: DemandeurDetailComponent;
  let fixture: ComponentFixture<DemandeurDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DemandeurDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DemandeurDetailComponent,
              resolve: { demandeur: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DemandeurDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DemandeurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load demandeur on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DemandeurDetailComponent);

      // THEN
      expect(instance.demandeur()).toEqual(expect.objectContaining({ id: 123 }));
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
