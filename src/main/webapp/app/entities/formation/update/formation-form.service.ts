import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFormation, NewFormation } from '../formation.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFormation for edit and NewFormationFormGroupInput for create.
 */
type FormationFormGroupInput = IFormation | PartialWithRequiredKeyOf<NewFormation>;

type FormationFormDefaults = Pick<NewFormation, 'id' | 'etablissements'>;

type FormationFormGroupContent = {
  id: FormControl<IFormation['id'] | NewFormation['id']>;
  nomFormation: FormControl<IFormation['nomFormation']>;
  imageFormation: FormControl<IFormation['imageFormation']>;
  imageFormationContentType: FormControl<IFormation['imageFormationContentType']>;
  typeFormation: FormControl<IFormation['typeFormation']>;
  duree: FormControl<IFormation['duree']>;
  admission: FormControl<IFormation['admission']>;
  diplomeRequis: FormControl<IFormation['diplomeRequis']>;
  ficheFormation: FormControl<IFormation['ficheFormation']>;
  ficheFormationContentType: FormControl<IFormation['ficheFormationContentType']>;
  etablissements: FormControl<IFormation['etablissements']>;
};

export type FormationFormGroup = FormGroup<FormationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FormationFormService {
  createFormationFormGroup(formation: FormationFormGroupInput = { id: null }): FormationFormGroup {
    const formationRawValue = {
      ...this.getFormDefaults(),
      ...formation,
    };
    return new FormGroup<FormationFormGroupContent>({
      id: new FormControl(
        { value: formationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nomFormation: new FormControl(formationRawValue.nomFormation),
      imageFormation: new FormControl(formationRawValue.imageFormation),
      imageFormationContentType: new FormControl(formationRawValue.imageFormationContentType),
      typeFormation: new FormControl(formationRawValue.typeFormation),
      duree: new FormControl(formationRawValue.duree),
      admission: new FormControl(formationRawValue.admission),
      diplomeRequis: new FormControl(formationRawValue.diplomeRequis),
      ficheFormation: new FormControl(formationRawValue.ficheFormation),
      ficheFormationContentType: new FormControl(formationRawValue.ficheFormationContentType),
      etablissements: new FormControl(formationRawValue.etablissements ?? []),
    });
  }

  getFormation(form: FormationFormGroup): IFormation | NewFormation {
    return form.getRawValue() as IFormation | NewFormation;
  }

  resetForm(form: FormationFormGroup, formation: FormationFormGroupInput): void {
    const formationRawValue = { ...this.getFormDefaults(), ...formation };
    form.reset(
      {
        ...formationRawValue,
        id: { value: formationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FormationFormDefaults {
    return {
      id: null,
      etablissements: [],
    };
  }
}
