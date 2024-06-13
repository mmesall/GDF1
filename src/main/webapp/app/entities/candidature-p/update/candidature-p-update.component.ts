import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { ProfessionnelService } from 'app/entities/professionnel/service/professionnel.service';
import { IFormationContinue } from 'app/entities/formation-continue/formation-continue.model';
import { FormationContinueService } from 'app/entities/formation-continue/service/formation-continue.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { NomFiliere } from 'app/entities/enumerations/nom-filiere.model';
import { Resultat } from 'app/entities/enumerations/resultat.model';
import { CandidaturePService } from '../service/candidature-p.service';
import { ICandidatureP } from '../candidature-p.model';
import { CandidaturePFormService, CandidaturePFormGroup } from './candidature-p-form.service';

@Component({
  standalone: true,
  selector: 'jhi-candidature-p-update',
  templateUrl: './candidature-p-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CandidaturePUpdateComponent implements OnInit {
  isSaving = false;
  candidatureP: ICandidatureP | null = null;
  nomFiliereValues = Object.keys(NomFiliere);
  resultatValues = Object.keys(Resultat);

  professionnelsSharedCollection: IProfessionnel[] = [];
  formationContinuesSharedCollection: IFormationContinue[] = [];
  etablissementsSharedCollection: IEtablissement[] = [];

  protected candidaturePService = inject(CandidaturePService);
  protected candidaturePFormService = inject(CandidaturePFormService);
  protected professionnelService = inject(ProfessionnelService);
  protected formationContinueService = inject(FormationContinueService);
  protected etablissementService = inject(EtablissementService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CandidaturePFormGroup = this.candidaturePFormService.createCandidaturePFormGroup();

  compareProfessionnel = (o1: IProfessionnel | null, o2: IProfessionnel | null): boolean =>
    this.professionnelService.compareProfessionnel(o1, o2);

  compareFormationContinue = (o1: IFormationContinue | null, o2: IFormationContinue | null): boolean =>
    this.formationContinueService.compareFormationContinue(o1, o2);

  compareEtablissement = (o1: IEtablissement | null, o2: IEtablissement | null): boolean =>
    this.etablissementService.compareEtablissement(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidatureP }) => {
      this.candidatureP = candidatureP;
      if (candidatureP) {
        this.updateForm(candidatureP);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const candidatureP = this.candidaturePFormService.getCandidatureP(this.editForm);
    if (candidatureP.id !== null) {
      this.subscribeToSaveResponse(this.candidaturePService.update(candidatureP));
    } else {
      this.subscribeToSaveResponse(this.candidaturePService.create(candidatureP));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidatureP>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(candidatureP: ICandidatureP): void {
    this.candidatureP = candidatureP;
    this.candidaturePFormService.resetForm(this.editForm, candidatureP);

    this.professionnelsSharedCollection = this.professionnelService.addProfessionnelToCollectionIfMissing<IProfessionnel>(
      this.professionnelsSharedCollection,
      candidatureP.professionnel,
    );
    this.formationContinuesSharedCollection = this.formationContinueService.addFormationContinueToCollectionIfMissing<IFormationContinue>(
      this.formationContinuesSharedCollection,
      candidatureP.formationContinue,
    );
    this.etablissementsSharedCollection = this.etablissementService.addEtablissementToCollectionIfMissing<IEtablissement>(
      this.etablissementsSharedCollection,
      candidatureP.etablissement,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.professionnelService
      .query()
      .pipe(map((res: HttpResponse<IProfessionnel[]>) => res.body ?? []))
      .pipe(
        map((professionnels: IProfessionnel[]) =>
          this.professionnelService.addProfessionnelToCollectionIfMissing<IProfessionnel>(professionnels, this.candidatureP?.professionnel),
        ),
      )
      .subscribe((professionnels: IProfessionnel[]) => (this.professionnelsSharedCollection = professionnels));

    this.formationContinueService
      .query()
      .pipe(map((res: HttpResponse<IFormationContinue[]>) => res.body ?? []))
      .pipe(
        map((formationContinues: IFormationContinue[]) =>
          this.formationContinueService.addFormationContinueToCollectionIfMissing<IFormationContinue>(
            formationContinues,
            this.candidatureP?.formationContinue,
          ),
        ),
      )
      .subscribe((formationContinues: IFormationContinue[]) => (this.formationContinuesSharedCollection = formationContinues));

    this.etablissementService
      .query()
      .pipe(map((res: HttpResponse<IEtablissement[]>) => res.body ?? []))
      .pipe(
        map((etablissements: IEtablissement[]) =>
          this.etablissementService.addEtablissementToCollectionIfMissing<IEtablissement>(etablissements, this.candidatureP?.etablissement),
        ),
      )
      .subscribe((etablissements: IEtablissement[]) => (this.etablissementsSharedCollection = etablissements));
  }
}
