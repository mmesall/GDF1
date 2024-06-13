import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../formation-continue.test-samples';

import { FormationContinueFormService } from './formation-continue-form.service';

describe('FormationContinue Form Service', () => {
  let service: FormationContinueFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FormationContinueFormService);
  });

  describe('Service methods', () => {
    describe('createFormationContinueFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFormationContinueFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomFormationC: expect.any(Object),
            duree: expect.any(Object),
            admission: expect.any(Object),
            diplomeRequis: expect.any(Object),
            niveauEtude: expect.any(Object),
            filiere: expect.any(Object),
            serie: expect.any(Object),
            cfp: expect.any(Object),
            lycee: expect.any(Object),
            ficheFormation: expect.any(Object),
            libellePC: expect.any(Object),
            montantPriseEnCharge: expect.any(Object),
            coutFormation: expect.any(Object),
            detailPC: expect.any(Object),
            nomDiplome: expect.any(Object),
            autreDiplome: expect.any(Object),
            nomDebouche: expect.any(Object),
            formation: expect.any(Object),
          }),
        );
      });

      it('passing IFormationContinue should create a new form with FormGroup', () => {
        const formGroup = service.createFormationContinueFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomFormationC: expect.any(Object),
            duree: expect.any(Object),
            admission: expect.any(Object),
            diplomeRequis: expect.any(Object),
            niveauEtude: expect.any(Object),
            filiere: expect.any(Object),
            serie: expect.any(Object),
            cfp: expect.any(Object),
            lycee: expect.any(Object),
            ficheFormation: expect.any(Object),
            libellePC: expect.any(Object),
            montantPriseEnCharge: expect.any(Object),
            coutFormation: expect.any(Object),
            detailPC: expect.any(Object),
            nomDiplome: expect.any(Object),
            autreDiplome: expect.any(Object),
            nomDebouche: expect.any(Object),
            formation: expect.any(Object),
          }),
        );
      });
    });

    describe('getFormationContinue', () => {
      it('should return NewFormationContinue for default FormationContinue initial value', () => {
        const formGroup = service.createFormationContinueFormGroup(sampleWithNewData);

        const formationContinue = service.getFormationContinue(formGroup) as any;

        expect(formationContinue).toMatchObject(sampleWithNewData);
      });

      it('should return NewFormationContinue for empty FormationContinue initial value', () => {
        const formGroup = service.createFormationContinueFormGroup();

        const formationContinue = service.getFormationContinue(formGroup) as any;

        expect(formationContinue).toMatchObject({});
      });

      it('should return IFormationContinue', () => {
        const formGroup = service.createFormationContinueFormGroup(sampleWithRequiredData);

        const formationContinue = service.getFormationContinue(formGroup) as any;

        expect(formationContinue).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFormationContinue should not enable id FormControl', () => {
        const formGroup = service.createFormationContinueFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFormationContinue should disable id FormControl', () => {
        const formGroup = service.createFormationContinueFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
