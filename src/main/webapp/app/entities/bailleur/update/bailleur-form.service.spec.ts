import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../bailleur.test-samples';

import { BailleurFormService } from './bailleur-form.service';

describe('Bailleur Form Service', () => {
  let service: BailleurFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BailleurFormService);
  });

  describe('Service methods', () => {
    describe('createBailleurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBailleurFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomBailleur: expect.any(Object),
            budgetPrevu: expect.any(Object),
            budgetDepense: expect.any(Object),
            budgetRestant: expect.any(Object),
            nbrePC: expect.any(Object),
          }),
        );
      });

      it('passing IBailleur should create a new form with FormGroup', () => {
        const formGroup = service.createBailleurFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomBailleur: expect.any(Object),
            budgetPrevu: expect.any(Object),
            budgetDepense: expect.any(Object),
            budgetRestant: expect.any(Object),
            nbrePC: expect.any(Object),
          }),
        );
      });
    });

    describe('getBailleur', () => {
      it('should return NewBailleur for default Bailleur initial value', () => {
        const formGroup = service.createBailleurFormGroup(sampleWithNewData);

        const bailleur = service.getBailleur(formGroup) as any;

        expect(bailleur).toMatchObject(sampleWithNewData);
      });

      it('should return NewBailleur for empty Bailleur initial value', () => {
        const formGroup = service.createBailleurFormGroup();

        const bailleur = service.getBailleur(formGroup) as any;

        expect(bailleur).toMatchObject({});
      });

      it('should return IBailleur', () => {
        const formGroup = service.createBailleurFormGroup(sampleWithRequiredData);

        const bailleur = service.getBailleur(formGroup) as any;

        expect(bailleur).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBailleur should not enable id FormControl', () => {
        const formGroup = service.createBailleurFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBailleur should disable id FormControl', () => {
        const formGroup = service.createBailleurFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
