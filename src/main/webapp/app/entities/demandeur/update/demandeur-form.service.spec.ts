import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../demandeur.test-samples';

import { DemandeurFormService } from './demandeur-form.service';

describe('Demandeur Form Service', () => {
  let service: DemandeurFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DemandeurFormService);
  });

  describe('Service methods', () => {
    describe('createDemandeurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDemandeurFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            prenom: expect.any(Object),
            dateNaiss: expect.any(Object),
            lieuNaiss: expect.any(Object),
            sexe: expect.any(Object),
            telephone: expect.any(Object),
            email: expect.any(Object),
            profil: expect.any(Object),
            user: expect.any(Object),
            dossier: expect.any(Object),
            eleve: expect.any(Object),
            etudiant: expect.any(Object),
            professionnel: expect.any(Object),
          }),
        );
      });

      it('passing IDemandeur should create a new form with FormGroup', () => {
        const formGroup = service.createDemandeurFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            prenom: expect.any(Object),
            dateNaiss: expect.any(Object),
            lieuNaiss: expect.any(Object),
            sexe: expect.any(Object),
            telephone: expect.any(Object),
            email: expect.any(Object),
            profil: expect.any(Object),
            user: expect.any(Object),
            dossier: expect.any(Object),
            eleve: expect.any(Object),
            etudiant: expect.any(Object),
            professionnel: expect.any(Object),
          }),
        );
      });
    });

    describe('getDemandeur', () => {
      it('should return NewDemandeur for default Demandeur initial value', () => {
        const formGroup = service.createDemandeurFormGroup(sampleWithNewData);

        const demandeur = service.getDemandeur(formGroup) as any;

        expect(demandeur).toMatchObject(sampleWithNewData);
      });

      it('should return NewDemandeur for empty Demandeur initial value', () => {
        const formGroup = service.createDemandeurFormGroup();

        const demandeur = service.getDemandeur(formGroup) as any;

        expect(demandeur).toMatchObject({});
      });

      it('should return IDemandeur', () => {
        const formGroup = service.createDemandeurFormGroup(sampleWithRequiredData);

        const demandeur = service.getDemandeur(formGroup) as any;

        expect(demandeur).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDemandeur should not enable id FormControl', () => {
        const formGroup = service.createDemandeurFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDemandeur should disable id FormControl', () => {
        const formGroup = service.createDemandeurFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
