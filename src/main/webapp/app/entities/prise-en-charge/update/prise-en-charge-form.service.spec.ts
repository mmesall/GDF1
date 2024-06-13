import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../prise-en-charge.test-samples';

import { PriseEnChargeFormService } from './prise-en-charge-form.service';

describe('PriseEnCharge Form Service', () => {
  let service: PriseEnChargeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PriseEnChargeFormService);
  });

  describe('Service methods', () => {
    describe('createPriseEnChargeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPriseEnChargeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
            montantPC: expect.any(Object),
            formation: expect.any(Object),
            bailleur: expect.any(Object),
          }),
        );
      });

      it('passing IPriseEnCharge should create a new form with FormGroup', () => {
        const formGroup = service.createPriseEnChargeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
            montantPC: expect.any(Object),
            formation: expect.any(Object),
            bailleur: expect.any(Object),
          }),
        );
      });
    });

    describe('getPriseEnCharge', () => {
      it('should return NewPriseEnCharge for default PriseEnCharge initial value', () => {
        const formGroup = service.createPriseEnChargeFormGroup(sampleWithNewData);

        const priseEnCharge = service.getPriseEnCharge(formGroup) as any;

        expect(priseEnCharge).toMatchObject(sampleWithNewData);
      });

      it('should return NewPriseEnCharge for empty PriseEnCharge initial value', () => {
        const formGroup = service.createPriseEnChargeFormGroup();

        const priseEnCharge = service.getPriseEnCharge(formGroup) as any;

        expect(priseEnCharge).toMatchObject({});
      });

      it('should return IPriseEnCharge', () => {
        const formGroup = service.createPriseEnChargeFormGroup(sampleWithRequiredData);

        const priseEnCharge = service.getPriseEnCharge(formGroup) as any;

        expect(priseEnCharge).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPriseEnCharge should not enable id FormControl', () => {
        const formGroup = service.createPriseEnChargeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPriseEnCharge should disable id FormControl', () => {
        const formGroup = service.createPriseEnChargeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
