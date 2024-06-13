import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IConcours, NewConcours } from '../concours.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IConcours for edit and NewConcoursFormGroupInput for create.
 */
type ConcoursFormGroupInput = IConcours | PartialWithRequiredKeyOf<NewConcours>;

type ConcoursFormDefaults = Pick<NewConcours, 'id'>;

type ConcoursFormGroupContent = {
  id: FormControl<IConcours['id'] | NewConcours['id']>;
  nomConcours: FormControl<IConcours['nomConcours']>;
  nomEtablissement: FormControl<IConcours['nomEtablissement']>;
  niveauEtude: FormControl<IConcours['niveauEtude']>;
  dateOuverture: FormControl<IConcours['dateOuverture']>;
  dateCloture: FormControl<IConcours['dateCloture']>;
  dateConcours: FormControl<IConcours['dateConcours']>;
  affiche: FormControl<IConcours['affiche']>;
  afficheContentType: FormControl<IConcours['afficheContentType']>;
  formation: FormControl<IConcours['formation']>;
};

export type ConcoursFormGroup = FormGroup<ConcoursFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ConcoursFormService {
  createConcoursFormGroup(concours: ConcoursFormGroupInput = { id: null }): ConcoursFormGroup {
    const concoursRawValue = {
      ...this.getFormDefaults(),
      ...concours,
    };
    return new FormGroup<ConcoursFormGroupContent>({
      id: new FormControl(
        { value: concoursRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nomConcours: new FormControl(concoursRawValue.nomConcours),
      nomEtablissement: new FormControl(concoursRawValue.nomEtablissement),
      niveauEtude: new FormControl(concoursRawValue.niveauEtude),
      dateOuverture: new FormControl(concoursRawValue.dateOuverture),
      dateCloture: new FormControl(concoursRawValue.dateCloture),
      dateConcours: new FormControl(concoursRawValue.dateConcours),
      affiche: new FormControl(concoursRawValue.affiche),
      afficheContentType: new FormControl(concoursRawValue.afficheContentType),
      formation: new FormControl(concoursRawValue.formation),
    });
  }

  getConcours(form: ConcoursFormGroup): IConcours | NewConcours {
    return form.getRawValue() as IConcours | NewConcours;
  }

  resetForm(form: ConcoursFormGroup, concours: ConcoursFormGroupInput): void {
    const concoursRawValue = { ...this.getFormDefaults(), ...concours };
    form.reset(
      {
        ...concoursRawValue,
        id: { value: concoursRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ConcoursFormDefaults {
    return {
      id: null,
    };
  }
}
