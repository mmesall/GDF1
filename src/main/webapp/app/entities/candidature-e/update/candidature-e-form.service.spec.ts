import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../candidature-e.test-samples';

import { CandidatureEFormService } from './candidature-e-form.service';

describe('CandidatureE Form Service', () => {
  let service: CandidatureEFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CandidatureEFormService);
  });

  describe('Service methods', () => {
    describe('createCandidatureEFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCandidatureEFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            offreFormation: expect.any(Object),
            dateDebutOffre: expect.any(Object),
            dateFinOffre: expect.any(Object),
            dateDepot: expect.any(Object),
            resultat: expect.any(Object),
            eleve: expect.any(Object),
            etudiant: expect.any(Object),
            formationInitiale: expect.any(Object),
            etablissement: expect.any(Object),
          }),
        );
      });

      it('passing ICandidatureE should create a new form with FormGroup', () => {
        const formGroup = service.createCandidatureEFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            offreFormation: expect.any(Object),
            dateDebutOffre: expect.any(Object),
            dateFinOffre: expect.any(Object),
            dateDepot: expect.any(Object),
            resultat: expect.any(Object),
            eleve: expect.any(Object),
            etudiant: expect.any(Object),
            formationInitiale: expect.any(Object),
            etablissement: expect.any(Object),
          }),
        );
      });
    });

    describe('getCandidatureE', () => {
      it('should return NewCandidatureE for default CandidatureE initial value', () => {
        const formGroup = service.createCandidatureEFormGroup(sampleWithNewData);

        const candidatureE = service.getCandidatureE(formGroup) as any;

        expect(candidatureE).toMatchObject(sampleWithNewData);
      });

      it('should return NewCandidatureE for empty CandidatureE initial value', () => {
        const formGroup = service.createCandidatureEFormGroup();

        const candidatureE = service.getCandidatureE(formGroup) as any;

        expect(candidatureE).toMatchObject({});
      });

      it('should return ICandidatureE', () => {
        const formGroup = service.createCandidatureEFormGroup(sampleWithRequiredData);

        const candidatureE = service.getCandidatureE(formGroup) as any;

        expect(candidatureE).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICandidatureE should not enable id FormControl', () => {
        const formGroup = service.createCandidatureEFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCandidatureE should disable id FormControl', () => {
        const formGroup = service.createCandidatureEFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
