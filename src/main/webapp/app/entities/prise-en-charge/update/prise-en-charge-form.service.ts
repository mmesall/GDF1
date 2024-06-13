import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPriseEnCharge, NewPriseEnCharge } from '../prise-en-charge.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPriseEnCharge for edit and NewPriseEnChargeFormGroupInput for create.
 */
type PriseEnChargeFormGroupInput = IPriseEnCharge | PartialWithRequiredKeyOf<NewPriseEnCharge>;

type PriseEnChargeFormDefaults = Pick<NewPriseEnCharge, 'id'>;

type PriseEnChargeFormGroupContent = {
  id: FormControl<IPriseEnCharge['id'] | NewPriseEnCharge['id']>;
  libelle: FormControl<IPriseEnCharge['libelle']>;
  montantPC: FormControl<IPriseEnCharge['montantPC']>;
  formation: FormControl<IPriseEnCharge['formation']>;
  bailleur: FormControl<IPriseEnCharge['bailleur']>;
};

export type PriseEnChargeFormGroup = FormGroup<PriseEnChargeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PriseEnChargeFormService {
  createPriseEnChargeFormGroup(priseEnCharge: PriseEnChargeFormGroupInput = { id: null }): PriseEnChargeFormGroup {
    const priseEnChargeRawValue = {
      ...this.getFormDefaults(),
      ...priseEnCharge,
    };
    return new FormGroup<PriseEnChargeFormGroupContent>({
      id: new FormControl(
        { value: priseEnChargeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      libelle: new FormControl(priseEnChargeRawValue.libelle),
      montantPC: new FormControl(priseEnChargeRawValue.montantPC),
      formation: new FormControl(priseEnChargeRawValue.formation),
      bailleur: new FormControl(priseEnChargeRawValue.bailleur),
    });
  }

  getPriseEnCharge(form: PriseEnChargeFormGroup): IPriseEnCharge | NewPriseEnCharge {
    return form.getRawValue() as IPriseEnCharge | NewPriseEnCharge;
  }

  resetForm(form: PriseEnChargeFormGroup, priseEnCharge: PriseEnChargeFormGroupInput): void {
    const priseEnChargeRawValue = { ...this.getFormDefaults(), ...priseEnCharge };
    form.reset(
      {
        ...priseEnChargeRawValue,
        id: { value: priseEnChargeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PriseEnChargeFormDefaults {
    return {
      id: null,
    };
  }
}
