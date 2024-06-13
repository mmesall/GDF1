import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFormationContinue, NewFormationContinue } from '../formation-continue.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFormationContinue for edit and NewFormationContinueFormGroupInput for create.
 */
type FormationContinueFormGroupInput = IFormationContinue | PartialWithRequiredKeyOf<NewFormationContinue>;

type FormationContinueFormDefaults = Pick<NewFormationContinue, 'id'>;

type FormationContinueFormGroupContent = {
  id: FormControl<IFormationContinue['id'] | NewFormationContinue['id']>;
  nomFormationC: FormControl<IFormationContinue['nomFormationC']>;
  duree: FormControl<IFormationContinue['duree']>;
  admission: FormControl<IFormationContinue['admission']>;
  diplomeRequis: FormControl<IFormationContinue['diplomeRequis']>;
  niveauEtude: FormControl<IFormationContinue['niveauEtude']>;
  filiere: FormControl<IFormationContinue['filiere']>;
  serie: FormControl<IFormationContinue['serie']>;
  cfp: FormControl<IFormationContinue['cfp']>;
  lycee: FormControl<IFormationContinue['lycee']>;
  ficheFormation: FormControl<IFormationContinue['ficheFormation']>;
  ficheFormationContentType: FormControl<IFormationContinue['ficheFormationContentType']>;
  libellePC: FormControl<IFormationContinue['libellePC']>;
  montantPriseEnCharge: FormControl<IFormationContinue['montantPriseEnCharge']>;
  coutFormation: FormControl<IFormationContinue['coutFormation']>;
  detailPC: FormControl<IFormationContinue['detailPC']>;
  nomDiplome: FormControl<IFormationContinue['nomDiplome']>;
  autreDiplome: FormControl<IFormationContinue['autreDiplome']>;
  nomDebouche: FormControl<IFormationContinue['nomDebouche']>;
  formation: FormControl<IFormationContinue['formation']>;
};

export type FormationContinueFormGroup = FormGroup<FormationContinueFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FormationContinueFormService {
  createFormationContinueFormGroup(formationContinue: FormationContinueFormGroupInput = { id: null }): FormationContinueFormGroup {
    const formationContinueRawValue = {
      ...this.getFormDefaults(),
      ...formationContinue,
    };
    return new FormGroup<FormationContinueFormGroupContent>({
      id: new FormControl(
        { value: formationContinueRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nomFormationC: new FormControl(formationContinueRawValue.nomFormationC),
      duree: new FormControl(formationContinueRawValue.duree),
      admission: new FormControl(formationContinueRawValue.admission),
      diplomeRequis: new FormControl(formationContinueRawValue.diplomeRequis),
      niveauEtude: new FormControl(formationContinueRawValue.niveauEtude),
      filiere: new FormControl(formationContinueRawValue.filiere),
      serie: new FormControl(formationContinueRawValue.serie),
      cfp: new FormControl(formationContinueRawValue.cfp),
      lycee: new FormControl(formationContinueRawValue.lycee),
      ficheFormation: new FormControl(formationContinueRawValue.ficheFormation),
      ficheFormationContentType: new FormControl(formationContinueRawValue.ficheFormationContentType),
      libellePC: new FormControl(formationContinueRawValue.libellePC),
      montantPriseEnCharge: new FormControl(formationContinueRawValue.montantPriseEnCharge),
      coutFormation: new FormControl(formationContinueRawValue.coutFormation),
      detailPC: new FormControl(formationContinueRawValue.detailPC),
      nomDiplome: new FormControl(formationContinueRawValue.nomDiplome),
      autreDiplome: new FormControl(formationContinueRawValue.autreDiplome),
      nomDebouche: new FormControl(formationContinueRawValue.nomDebouche),
      formation: new FormControl(formationContinueRawValue.formation),
    });
  }

  getFormationContinue(form: FormationContinueFormGroup): IFormationContinue | NewFormationContinue {
    return form.getRawValue() as IFormationContinue | NewFormationContinue;
  }

  resetForm(form: FormationContinueFormGroup, formationContinue: FormationContinueFormGroupInput): void {
    const formationContinueRawValue = { ...this.getFormDefaults(), ...formationContinue };
    form.reset(
      {
        ...formationContinueRawValue,
        id: { value: formationContinueRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FormationContinueFormDefaults {
    return {
      id: null,
    };
  }
}
