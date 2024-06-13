import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBailleur, NewBailleur } from '../bailleur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBailleur for edit and NewBailleurFormGroupInput for create.
 */
type BailleurFormGroupInput = IBailleur | PartialWithRequiredKeyOf<NewBailleur>;

type BailleurFormDefaults = Pick<NewBailleur, 'id'>;

type BailleurFormGroupContent = {
  id: FormControl<IBailleur['id'] | NewBailleur['id']>;
  nomBailleur: FormControl<IBailleur['nomBailleur']>;
  budgetPrevu: FormControl<IBailleur['budgetPrevu']>;
  budgetDepense: FormControl<IBailleur['budgetDepense']>;
  budgetRestant: FormControl<IBailleur['budgetRestant']>;
  nbrePC: FormControl<IBailleur['nbrePC']>;
};

export type BailleurFormGroup = FormGroup<BailleurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BailleurFormService {
  createBailleurFormGroup(bailleur: BailleurFormGroupInput = { id: null }): BailleurFormGroup {
    const bailleurRawValue = {
      ...this.getFormDefaults(),
      ...bailleur,
    };
    return new FormGroup<BailleurFormGroupContent>({
      id: new FormControl(
        { value: bailleurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nomBailleur: new FormControl(bailleurRawValue.nomBailleur, {
        validators: [Validators.required],
      }),
      budgetPrevu: new FormControl(bailleurRawValue.budgetPrevu),
      budgetDepense: new FormControl(bailleurRawValue.budgetDepense),
      budgetRestant: new FormControl(bailleurRawValue.budgetRestant),
      nbrePC: new FormControl(bailleurRawValue.nbrePC),
    });
  }

  getBailleur(form: BailleurFormGroup): IBailleur | NewBailleur {
    return form.getRawValue() as IBailleur | NewBailleur;
  }

  resetForm(form: BailleurFormGroup, bailleur: BailleurFormGroupInput): void {
    const bailleurRawValue = { ...this.getFormDefaults(), ...bailleur };
    form.reset(
      {
        ...bailleurRawValue,
        id: { value: bailleurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BailleurFormDefaults {
    return {
      id: null,
    };
  }
}
