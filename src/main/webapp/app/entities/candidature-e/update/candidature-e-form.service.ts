import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICandidatureE, NewCandidatureE } from '../candidature-e.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICandidatureE for edit and NewCandidatureEFormGroupInput for create.
 */
type CandidatureEFormGroupInput = ICandidatureE | PartialWithRequiredKeyOf<NewCandidatureE>;

type CandidatureEFormDefaults = Pick<NewCandidatureE, 'id'>;

type CandidatureEFormGroupContent = {
  id: FormControl<ICandidatureE['id'] | NewCandidatureE['id']>;
  offreFormation: FormControl<ICandidatureE['offreFormation']>;
  dateDebutOffre: FormControl<ICandidatureE['dateDebutOffre']>;
  dateFinOffre: FormControl<ICandidatureE['dateFinOffre']>;
  dateDepot: FormControl<ICandidatureE['dateDepot']>;
  resultat: FormControl<ICandidatureE['resultat']>;
  eleve: FormControl<ICandidatureE['eleve']>;
  etudiant: FormControl<ICandidatureE['etudiant']>;
  formationInitiale: FormControl<ICandidatureE['formationInitiale']>;
  etablissement: FormControl<ICandidatureE['etablissement']>;
};

export type CandidatureEFormGroup = FormGroup<CandidatureEFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CandidatureEFormService {
  createCandidatureEFormGroup(candidatureE: CandidatureEFormGroupInput = { id: null }): CandidatureEFormGroup {
    const candidatureERawValue = {
      ...this.getFormDefaults(),
      ...candidatureE,
    };
    return new FormGroup<CandidatureEFormGroupContent>({
      id: new FormControl(
        { value: candidatureERawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      offreFormation: new FormControl(candidatureERawValue.offreFormation),
      dateDebutOffre: new FormControl(candidatureERawValue.dateDebutOffre),
      dateFinOffre: new FormControl(candidatureERawValue.dateFinOffre),
      dateDepot: new FormControl(candidatureERawValue.dateDepot),
      resultat: new FormControl(candidatureERawValue.resultat),
      eleve: new FormControl(candidatureERawValue.eleve),
      etudiant: new FormControl(candidatureERawValue.etudiant),
      formationInitiale: new FormControl(candidatureERawValue.formationInitiale),
      etablissement: new FormControl(candidatureERawValue.etablissement),
    });
  }

  getCandidatureE(form: CandidatureEFormGroup): ICandidatureE | NewCandidatureE {
    return form.getRawValue() as ICandidatureE | NewCandidatureE;
  }

  resetForm(form: CandidatureEFormGroup, candidatureE: CandidatureEFormGroupInput): void {
    const candidatureERawValue = { ...this.getFormDefaults(), ...candidatureE };
    form.reset(
      {
        ...candidatureERawValue,
        id: { value: candidatureERawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CandidatureEFormDefaults {
    return {
      id: null,
    };
  }
}
