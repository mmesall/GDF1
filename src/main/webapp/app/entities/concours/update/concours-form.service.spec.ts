import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../concours.test-samples';

import { ConcoursFormService } from './concours-form.service';

describe('Concours Form Service', () => {
  let service: ConcoursFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConcoursFormService);
  });

  describe('Service methods', () => {
    describe('createConcoursFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createConcoursFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomConcours: expect.any(Object),
            nomEtablissement: expect.any(Object),
            niveauEtude: expect.any(Object),
            dateOuverture: expect.any(Object),
            dateCloture: expect.any(Object),
            dateConcours: expect.any(Object),
            affiche: expect.any(Object),
            formation: expect.any(Object),
          }),
        );
      });

      it('passing IConcours should create a new form with FormGroup', () => {
        const formGroup = service.createConcoursFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomConcours: expect.any(Object),
            nomEtablissement: expect.any(Object),
            niveauEtude: expect.any(Object),
            dateOuverture: expect.any(Object),
            dateCloture: expect.any(Object),
            dateConcours: expect.any(Object),
            affiche: expect.any(Object),
            formation: expect.any(Object),
          }),
        );
      });
    });

    describe('getConcours', () => {
      it('should return NewConcours for default Concours initial value', () => {
        const formGroup = service.createConcoursFormGroup(sampleWithNewData);

        const concours = service.getConcours(formGroup) as any;

        expect(concours).toMatchObject(sampleWithNewData);
      });

      it('should return NewConcours for empty Concours initial value', () => {
        const formGroup = service.createConcoursFormGroup();

        const concours = service.getConcours(formGroup) as any;

        expect(concours).toMatchObject({});
      });

      it('should return IConcours', () => {
        const formGroup = service.createConcoursFormGroup(sampleWithRequiredData);

        const concours = service.getConcours(formGroup) as any;

        expect(concours).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IConcours should not enable id FormControl', () => {
        const formGroup = service.createConcoursFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewConcours should disable id FormControl', () => {
        const formGroup = service.createConcoursFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
