import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDiplome, NewDiplome } from '../diplome.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDiplome for edit and NewDiplomeFormGroupInput for create.
 */
type DiplomeFormGroupInput = IDiplome | PartialWithRequiredKeyOf<NewDiplome>;

type DiplomeFormDefaults = Pick<NewDiplome, 'id'>;

type DiplomeFormGroupContent = {
  id: FormControl<IDiplome['id'] | NewDiplome['id']>;
  intitule: FormControl<IDiplome['intitule']>;
  domaine: FormControl<IDiplome['domaine']>;
  niveau: FormControl<IDiplome['niveau']>;
  mention: FormControl<IDiplome['mention']>;
  anneeObtention: FormControl<IDiplome['anneeObtention']>;
  etablissement: FormControl<IDiplome['etablissement']>;
  document: FormControl<IDiplome['document']>;
  documentContentType: FormControl<IDiplome['documentContentType']>;
  eleve: FormControl<IDiplome['eleve']>;
  etudiant: FormControl<IDiplome['etudiant']>;
  professionnel: FormControl<IDiplome['professionnel']>;
  demandeur: FormControl<IDiplome['demandeur']>;
};

export type DiplomeFormGroup = FormGroup<DiplomeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DiplomeFormService {
  createDiplomeFormGroup(diplome: DiplomeFormGroupInput = { id: null }): DiplomeFormGroup {
    const diplomeRawValue = {
      ...this.getFormDefaults(),
      ...diplome,
    };
    return new FormGroup<DiplomeFormGroupContent>({
      id: new FormControl(
        { value: diplomeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      intitule: new FormControl(diplomeRawValue.intitule),
      domaine: new FormControl(diplomeRawValue.domaine, {
        validators: [Validators.required],
      }),
      niveau: new FormControl(diplomeRawValue.niveau),
      mention: new FormControl(diplomeRawValue.mention),
      anneeObtention: new FormControl(diplomeRawValue.anneeObtention),
      etablissement: new FormControl(diplomeRawValue.etablissement),
      document: new FormControl(diplomeRawValue.document, {
        validators: [Validators.required],
      }),
      documentContentType: new FormControl(diplomeRawValue.documentContentType),
      eleve: new FormControl(diplomeRawValue.eleve),
      etudiant: new FormControl(diplomeRawValue.etudiant),
      professionnel: new FormControl(diplomeRawValue.professionnel),
      demandeur: new FormControl(diplomeRawValue.demandeur),
    });
  }

  getDiplome(form: DiplomeFormGroup): IDiplome | NewDiplome {
    return form.getRawValue() as IDiplome | NewDiplome;
  }

  resetForm(form: DiplomeFormGroup, diplome: DiplomeFormGroupInput): void {
    const diplomeRawValue = { ...this.getFormDefaults(), ...diplome };
    form.reset(
      {
        ...diplomeRawValue,
        id: { value: diplomeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DiplomeFormDefaults {
    return {
      id: null,
    };
  }
}
