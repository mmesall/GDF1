import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../candidature-p.test-samples';

import { CandidaturePFormService } from './candidature-p-form.service';

describe('CandidatureP Form Service', () => {
  let service: CandidaturePFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CandidaturePFormService);
  });

  describe('Service methods', () => {
    describe('createCandidaturePFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCandidaturePFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            offreFormation: expect.any(Object),
            dateDebutOffre: expect.any(Object),
            dateFinOffre: expect.any(Object),
            dateDepot: expect.any(Object),
            resultat: expect.any(Object),
            professionnel: expect.any(Object),
            formationContinue: expect.any(Object),
            etablissement: expect.any(Object),
          }),
        );
      });

      it('passing ICandidatureP should create a new form with FormGroup', () => {
        const formGroup = service.createCandidaturePFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            offreFormation: expect.any(Object),
            dateDebutOffre: expect.any(Object),
            dateFinOffre: expect.any(Object),
            dateDepot: expect.any(Object),
            resultat: expect.any(Object),
            professionnel: expect.any(Object),
            formationContinue: expect.any(Object),
            etablissement: expect.any(Object),
          }),
        );
      });
    });

    describe('getCandidatureP', () => {
      it('should return NewCandidatureP for default CandidatureP initial value', () => {
        const formGroup = service.createCandidaturePFormGroup(sampleWithNewData);

        const candidatureP = service.getCandidatureP(formGroup) as any;

        expect(candidatureP).toMatchObject(sampleWithNewData);
      });

      it('should return NewCandidatureP for empty CandidatureP initial value', () => {
        const formGroup = service.createCandidaturePFormGroup();

        const candidatureP = service.getCandidatureP(formGroup) as any;

        expect(candidatureP).toMatchObject({});
      });

      it('should return ICandidatureP', () => {
        const formGroup = service.createCandidaturePFormGroup(sampleWithRequiredData);

        const candidatureP = service.getCandidatureP(formGroup) as any;

        expect(candidatureP).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICandidatureP should not enable id FormControl', () => {
        const formGroup = service.createCandidaturePFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCandidatureP should disable id FormControl', () => {
        const formGroup = service.createCandidaturePFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
