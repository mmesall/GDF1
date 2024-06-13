import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICandidatureP, NewCandidatureP } from '../candidature-p.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICandidatureP for edit and NewCandidaturePFormGroupInput for create.
 */
type CandidaturePFormGroupInput = ICandidatureP | PartialWithRequiredKeyOf<NewCandidatureP>;

type CandidaturePFormDefaults = Pick<NewCandidatureP, 'id'>;

type CandidaturePFormGroupContent = {
  id: FormControl<ICandidatureP['id'] | NewCandidatureP['id']>;
  offreFormation: FormControl<ICandidatureP['offreFormation']>;
  dateDebutOffre: FormControl<ICandidatureP['dateDebutOffre']>;
  dateFinOffre: FormControl<ICandidatureP['dateFinOffre']>;
  dateDepot: FormControl<ICandidatureP['dateDepot']>;
  resultat: FormControl<ICandidatureP['resultat']>;
  professionnel: FormControl<ICandidatureP['professionnel']>;
  formationContinue: FormControl<ICandidatureP['formationContinue']>;
  etablissement: FormControl<ICandidatureP['etablissement']>;
};

export type CandidaturePFormGroup = FormGroup<CandidaturePFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CandidaturePFormService {
  createCandidaturePFormGroup(candidatureP: CandidaturePFormGroupInput = { id: null }): CandidaturePFormGroup {
    const candidaturePRawValue = {
      ...this.getFormDefaults(),
      ...candidatureP,
    };
    return new FormGroup<CandidaturePFormGroupContent>({
      id: new FormControl(
        { value: candidaturePRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      offreFormation: new FormControl(candidaturePRawValue.offreFormation),
      dateDebutOffre: new FormControl(candidaturePRawValue.dateDebutOffre),
      dateFinOffre: new FormControl(candidaturePRawValue.dateFinOffre),
      dateDepot: new FormControl(candidaturePRawValue.dateDepot),
      resultat: new FormControl(candidaturePRawValue.resultat),
      professionnel: new FormControl(candidaturePRawValue.professionnel),
      formationContinue: new FormControl(candidaturePRawValue.formationContinue),
      etablissement: new FormControl(candidaturePRawValue.etablissement),
    });
  }

  getCandidatureP(form: CandidaturePFormGroup): ICandidatureP | NewCandidatureP {
    return form.getRawValue() as ICandidatureP | NewCandidatureP;
  }

  resetForm(form: CandidaturePFormGroup, candidatureP: CandidaturePFormGroupInput): void {
    const candidaturePRawValue = { ...this.getFormDefaults(), ...candidatureP };
    form.reset(
      {
        ...candidaturePRawValue,
        id: { value: candidaturePRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CandidaturePFormDefaults {
    return {
      id: null,
    };
  }
}
