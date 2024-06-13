import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';
import { Admission } from 'app/entities/enumerations/admission.model';
import { DiplomeRequis } from 'app/entities/enumerations/diplome-requis.model';
import { NiveauEtude } from 'app/entities/enumerations/niveau-etude.model';
import { NomFiliere } from 'app/entities/enumerations/nom-filiere.model';
import { NomSerie } from 'app/entities/enumerations/nom-serie.model';
import { CFP } from 'app/entities/enumerations/cfp.model';
import { LYCEE } from 'app/entities/enumerations/lycee.model';
import { DiplomeObtenu } from 'app/entities/enumerations/diplome-obtenu.model';
import { FormationContinueService } from '../service/formation-continue.service';
import { IFormationContinue } from '../formation-continue.model';
import { FormationContinueFormService, FormationContinueFormGroup } from './formation-continue-form.service';

@Component({
  standalone: true,
  selector: 'jhi-formation-continue-update',
  templateUrl: './formation-continue-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FormationContinueUpdateComponent implements OnInit {
  isSaving = false;
  formationContinue: IFormationContinue | null = null;
  admissionValues = Object.keys(Admission);
  diplomeRequisValues = Object.keys(DiplomeRequis);
  niveauEtudeValues = Object.keys(NiveauEtude);
  nomFiliereValues = Object.keys(NomFiliere);
  nomSerieValues = Object.keys(NomSerie);
  cFPValues = Object.keys(CFP);
  lYCEEValues = Object.keys(LYCEE);
  diplomeObtenuValues = Object.keys(DiplomeObtenu);

  formationsCollection: IFormation[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected formationContinueService = inject(FormationContinueService);
  protected formationContinueFormService = inject(FormationContinueFormService);
  protected formationService = inject(FormationService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FormationContinueFormGroup = this.formationContinueFormService.createFormationContinueFormGroup();

  compareFormation = (o1: IFormation | null, o2: IFormation | null): boolean => this.formationService.compareFormation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formationContinue }) => {
      this.formationContinue = formationContinue;
      if (formationContinue) {
        this.updateForm(formationContinue);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('gdf1App.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const formationContinue = this.formationContinueFormService.getFormationContinue(this.editForm);
    if (formationContinue.id !== null) {
      this.subscribeToSaveResponse(this.formationContinueService.update(formationContinue));
    } else {
      this.subscribeToSaveResponse(this.formationContinueService.create(formationContinue));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormationContinue>>): void {
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

  protected updateForm(formationContinue: IFormationContinue): void {
    this.formationContinue = formationContinue;
    this.formationContinueFormService.resetForm(this.editForm, formationContinue);

    this.formationsCollection = this.formationService.addFormationToCollectionIfMissing<IFormation>(
      this.formationsCollection,
      formationContinue.formation,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.formationService
      .query({ filter: 'formationcontinue-is-null' })
      .pipe(map((res: HttpResponse<IFormation[]>) => res.body ?? []))
      .pipe(
        map((formations: IFormation[]) =>
          this.formationService.addFormationToCollectionIfMissing<IFormation>(formations, this.formationContinue?.formation),
        ),
      )
      .subscribe((formations: IFormation[]) => (this.formationsCollection = formations));
  }
}
