import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { BailleurDetailComponent } from './bailleur-detail.component';

describe('Bailleur Management Detail Component', () => {
  let comp: BailleurDetailComponent;
  let fixture: ComponentFixture<BailleurDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BailleurDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: BailleurDetailComponent,
              resolve: { bailleur: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(BailleurDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BailleurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bailleur on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', BailleurDetailComponent);

      // THEN
      expect(instance.bailleur()).toEqual(expect.objectContaining({ id: 123 }));
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
