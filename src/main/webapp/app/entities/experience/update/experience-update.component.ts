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
import { IEleve } from 'app/entities/eleve/eleve.model';
import { EleveService } from 'app/entities/eleve/service/eleve.service';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { EtudiantService } from 'app/entities/etudiant/service/etudiant.service';
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { ProfessionnelService } from 'app/entities/professionnel/service/professionnel.service';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { DemandeurService } from 'app/entities/demandeur/service/demandeur.service';
import { ExperienceService } from '../service/experience.service';
import { IExperience } from '../experience.model';
import { ExperienceFormService, ExperienceFormGroup } from './experience-form.service';

@Component({
  standalone: true,
  selector: 'jhi-experience-update',
  templateUrl: './experience-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ExperienceUpdateComponent implements OnInit {
  isSaving = false;
  experience: IExperience | null = null;

  elevesSharedCollection: IEleve[] = [];
  etudiantsSharedCollection: IEtudiant[] = [];
  professionnelsSharedCollection: IProfessionnel[] = [];
  demandeursSharedCollection: IDemandeur[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected experienceService = inject(ExperienceService);
  protected experienceFormService = inject(ExperienceFormService);
  protected eleveService = inject(EleveService);
  protected etudiantService = inject(EtudiantService);
  protected professionnelService = inject(ProfessionnelService);
  protected demandeurService = inject(DemandeurService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ExperienceFormGroup = this.experienceFormService.createExperienceFormGroup();

  compareEleve = (o1: IEleve | null, o2: IEleve | null): boolean => this.eleveService.compareEleve(o1, o2);

  compareEtudiant = (o1: IEtudiant | null, o2: IEtudiant | null): boolean => this.etudiantService.compareEtudiant(o1, o2);

  compareProfessionnel = (o1: IProfessionnel | null, o2: IProfessionnel | null): boolean =>
    this.professionnelService.compareProfessionnel(o1, o2);

  compareDemandeur = (o1: IDemandeur | null, o2: IDemandeur | null): boolean => this.demandeurService.compareDemandeur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ experience }) => {
      this.experience = experience;
      if (experience) {
        this.updateForm(experience);
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
    const experience = this.experienceFormService.getExperience(this.editForm);
    if (experience.id !== null) {
      this.subscribeToSaveResponse(this.experienceService.update(experience));
    } else {
      this.subscribeToSaveResponse(this.experienceService.create(experience));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExperience>>): void {
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

  protected updateForm(experience: IExperience): void {
    this.experience = experience;
    this.experienceFormService.resetForm(this.editForm, experience);

    this.elevesSharedCollection = this.eleveService.addEleveToCollectionIfMissing<IEleve>(this.elevesSharedCollection, experience.eleve);
    this.etudiantsSharedCollection = this.etudiantService.addEtudiantToCollectionIfMissing<IEtudiant>(
      this.etudiantsSharedCollection,
      experience.etudiant,
    );
    this.professionnelsSharedCollection = this.professionnelService.addProfessionnelToCollectionIfMissing<IProfessionnel>(
      this.professionnelsSharedCollection,
      experience.professionnel,
    );
    this.demandeursSharedCollection = this.demandeurService.addDemandeurToCollectionIfMissing<IDemandeur>(
      this.demandeursSharedCollection,
      experience.demandeur,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eleveService
      .query()
      .pipe(map((res: HttpResponse<IEleve[]>) => res.body ?? []))
      .pipe(map((eleves: IEleve[]) => this.eleveService.addEleveToCollectionIfMissing<IEleve>(eleves, this.experience?.eleve)))
      .subscribe((eleves: IEleve[]) => (this.elevesSharedCollection = eleves));

    this.etudiantService
      .query()
      .pipe(map((res: HttpResponse<IEtudiant[]>) => res.body ?? []))
      .pipe(
        map((etudiants: IEtudiant[]) =>
          this.etudiantService.addEtudiantToCollectionIfMissing<IEtudiant>(etudiants, this.experience?.etudiant),
        ),
      )
      .subscribe((etudiants: IEtudiant[]) => (this.etudiantsSharedCollection = etudiants));

    this.professionnelService
      .query()
      .pipe(map((res: HttpResponse<IProfessionnel[]>) => res.body ?? []))
      .pipe(
        map((professionnels: IProfessionnel[]) =>
          this.professionnelService.addProfessionnelToCollectionIfMissing<IProfessionnel>(professionnels, this.experience?.professionnel),
        ),
      )
      .subscribe((professionnels: IProfessionnel[]) => (this.professionnelsSharedCollection = professionnels));

    this.demandeurService
      .query()
      .pipe(map((res: HttpResponse<IDemandeur[]>) => res.body ?? []))
      .pipe(
        map((demandeurs: IDemandeur[]) =>
          this.demandeurService.addDemandeurToCollectionIfMissing<IDemandeur>(demandeurs, this.experience?.demandeur),
        ),
      )
      .subscribe((demandeurs: IDemandeur[]) => (this.demandeursSharedCollection = demandeurs));
  }
}
