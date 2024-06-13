import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../experience.test-samples';

import { ExperienceFormService } from './experience-form.service';

describe('Experience Form Service', () => {
  let service: ExperienceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExperienceFormService);
  });

  describe('Service methods', () => {
    describe('createExperienceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createExperienceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dateDebut: expect.any(Object),
            dateFin: expect.any(Object),
            nomEntreprise: expect.any(Object),
            posteOccupe: expect.any(Object),
            mission: expect.any(Object),
            eleve: expect.any(Object),
            etudiant: expect.any(Object),
            professionnel: expect.any(Object),
            demandeur: expect.any(Object),
          }),
        );
      });

      it('passing IExperience should create a new form with FormGroup', () => {
        const formGroup = service.createExperienceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dateDebut: expect.any(Object),
            dateFin: expect.any(Object),
            nomEntreprise: expect.any(Object),
            posteOccupe: expect.any(Object),
            mission: expect.any(Object),
            eleve: expect.any(Object),
            etudiant: expect.any(Object),
            professionnel: expect.any(Object),
            demandeur: expect.any(Object),
          }),
        );
      });
    });

    describe('getExperience', () => {
      it('should return NewExperience for default Experience initial value', () => {
        const formGroup = service.createExperienceFormGroup(sampleWithNewData);

        const experience = service.getExperience(formGroup) as any;

        expect(experience).toMatchObject(sampleWithNewData);
      });

      it('should return NewExperience for empty Experience initial value', () => {
        const formGroup = service.createExperienceFormGroup();

        const experience = service.getExperience(formGroup) as any;

        expect(experience).toMatchObject({});
      });

      it('should return IExperience', () => {
        const formGroup = service.createExperienceFormGroup(sampleWithRequiredData);

        const experience = service.getExperience(formGroup) as any;

        expect(experience).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IExperience should not enable id FormControl', () => {
        const formGroup = service.createExperienceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewExperience should disable id FormControl', () => {
        const formGroup = service.createExperienceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
