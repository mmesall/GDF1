import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFormationInitiale, NewFormationInitiale } from '../formation-initiale.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFormationInitiale for edit and NewFormationInitialeFormGroupInput for create.
 */
type FormationInitialeFormGroupInput = IFormationInitiale | PartialWithRequiredKeyOf<NewFormationInitiale>;

type FormationInitialeFormDefaults = Pick<NewFormationInitiale, 'id'>;

type FormationInitialeFormGroupContent = {
  id: FormControl<IFormationInitiale['id'] | NewFormationInitiale['id']>;
  nomFormationI: FormControl<IFormationInitiale['nomFormationI']>;
  duree: FormControl<IFormationInitiale['duree']>;
  admission: FormControl<IFormationInitiale['admission']>;
  diplomeRequis: FormControl<IFormationInitiale['diplomeRequis']>;
  niveauEtude: FormControl<IFormationInitiale['niveauEtude']>;
  ficheFormation: FormControl<IFormationInitiale['ficheFormation']>;
  ficheFormationContentType: FormControl<IFormationInitiale['ficheFormationContentType']>;
  filiere: FormControl<IFormationInitiale['filiere']>;
  serie: FormControl<IFormationInitiale['serie']>;
  cfp: FormControl<IFormationInitiale['cfp']>;
  lycee: FormControl<IFormationInitiale['lycee']>;
  nomConcours: FormControl<IFormationInitiale['nomConcours']>;
  dateOuverture: FormControl<IFormationInitiale['dateOuverture']>;
  dateCloture: FormControl<IFormationInitiale['dateCloture']>;
  dateConcours: FormControl<IFormationInitiale['dateConcours']>;
  nomDiplome: FormControl<IFormationInitiale['nomDiplome']>;
  nomDebouche: FormControl<IFormationInitiale['nomDebouche']>;
  formation: FormControl<IFormationInitiale['formation']>;
};

export type FormationInitialeFormGroup = FormGroup<FormationInitialeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FormationInitialeFormService {
  createFormationInitialeFormGroup(formationInitiale: FormationInitialeFormGroupInput = { id: null }): FormationInitialeFormGroup {
    const formationInitialeRawValue = {
      ...this.getFormDefaults(),
      ...formationInitiale,
    };
    return new FormGroup<FormationInitialeFormGroupContent>({
      id: new FormControl(
        { value: formationInitialeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nomFormationI: new FormControl(formationInitialeRawValue.nomFormationI),
      duree: new FormControl(formationInitialeRawValue.duree),
      admission: new FormControl(formationInitialeRawValue.admission),
      diplomeRequis: new FormControl(formationInitialeRawValue.diplomeRequis),
      niveauEtude: new FormControl(formationInitialeRawValue.niveauEtude),
      ficheFormation: new FormControl(formationInitialeRawValue.ficheFormation),
      ficheFormationContentType: new FormControl(formationInitialeRawValue.ficheFormationContentType),
      filiere: new FormControl(formationInitialeRawValue.filiere),
      serie: new FormControl(formationInitialeRawValue.serie),
      cfp: new FormControl(formationInitialeRawValue.cfp),
      lycee: new FormControl(formationInitialeRawValue.lycee),
      nomConcours: new FormControl(formationInitialeRawValue.nomConcours),
      dateOuverture: new FormControl(formationInitialeRawValue.dateOuverture),
      dateCloture: new FormControl(formationInitialeRawValue.dateCloture),
      dateConcours: new FormControl(formationInitialeRawValue.dateConcours),
      nomDiplome: new FormControl(formationInitialeRawValue.nomDiplome),
      nomDebouche: new FormControl(formationInitialeRawValue.nomDebouche),
      formation: new FormControl(formationInitialeRawValue.formation),
    });
  }

  getFormationInitiale(form: FormationInitialeFormGroup): IFormationInitiale | NewFormationInitiale {
    return form.getRawValue() as IFormationInitiale | NewFormationInitiale;
  }

  resetForm(form: FormationInitialeFormGroup, formationInitiale: FormationInitialeFormGroupInput): void {
    const formationInitialeRawValue = { ...this.getFormDefaults(), ...formationInitiale };
    form.reset(
      {
        ...formationInitialeRawValue,
        id: { value: formationInitialeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FormationInitialeFormDefaults {
    return {
      id: null,
    };
  }
}
