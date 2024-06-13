import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../formation-initiale.test-samples';

import { FormationInitialeFormService } from './formation-initiale-form.service';

describe('FormationInitiale Form Service', () => {
  let service: FormationInitialeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FormationInitialeFormService);
  });

  describe('Service methods', () => {
    describe('createFormationInitialeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFormationInitialeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomFormationI: expect.any(Object),
            duree: expect.any(Object),
            admission: expect.any(Object),
            diplomeRequis: expect.any(Object),
            niveauEtude: expect.any(Object),
            ficheFormation: expect.any(Object),
            filiere: expect.any(Object),
            serie: expect.any(Object),
            cfp: expect.any(Object),
            lycee: expect.any(Object),
            nomConcours: expect.any(Object),
            dateOuverture: expect.any(Object),
            dateCloture: expect.any(Object),
            dateConcours: expect.any(Object),
            nomDiplome: expect.any(Object),
            nomDebouche: expect.any(Object),
            formation: expect.any(Object),
          }),
        );
      });

      it('passing IFormationInitiale should create a new form with FormGroup', () => {
        const formGroup = service.createFormationInitialeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomFormationI: expect.any(Object),
            duree: expect.any(Object),
            admission: expect.any(Object),
            diplomeRequis: expect.any(Object),
            niveauEtude: expect.any(Object),
            ficheFormation: expect.any(Object),
            filiere: expect.any(Object),
            serie: expect.any(Object),
            cfp: expect.any(Object),
            lycee: expect.any(Object),
            nomConcours: expect.any(Object),
            dateOuverture: expect.any(Object),
            dateCloture: expect.any(Object),
            dateConcours: expect.any(Object),
            nomDiplome: expect.any(Object),
            nomDebouche: expect.any(Object),
            formation: expect.any(Object),
          }),
        );
      });
    });

    describe('getFormationInitiale', () => {
      it('should return NewFormationInitiale for default FormationInitiale initial value', () => {
        const formGroup = service.createFormationInitialeFormGroup(sampleWithNewData);

        const formationInitiale = service.getFormationInitiale(formGroup) as any;

        expect(formationInitiale).toMatchObject(sampleWithNewData);
      });

      it('should return NewFormationInitiale for empty FormationInitiale initial value', () => {
        const formGroup = service.createFormationInitialeFormGroup();

        const formationInitiale = service.getFormationInitiale(formGroup) as any;

        expect(formationInitiale).toMatchObject({});
      });

      it('should return IFormationInitiale', () => {
        const formGroup = service.createFormationInitialeFormGroup(sampleWithRequiredData);

        const formationInitiale = service.getFormationInitiale(formGroup) as any;

        expect(formationInitiale).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFormationInitiale should not enable id FormControl', () => {
        const formGroup = service.createFormationInitialeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFormationInitiale should disable id FormControl', () => {
        const formGroup = service.createFormationInitialeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
