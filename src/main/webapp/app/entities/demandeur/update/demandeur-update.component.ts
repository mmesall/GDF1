import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { EleveService } from 'app/entities/eleve/service/eleve.service';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { EtudiantService } from 'app/entities/etudiant/service/etudiant.service';
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { ProfessionnelService } from 'app/entities/professionnel/service/professionnel.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { Profil } from 'app/entities/enumerations/profil.model';
import { DemandeurService } from '../service/demandeur.service';
import { IDemandeur } from '../demandeur.model';
import { DemandeurFormService, DemandeurFormGroup } from './demandeur-form.service';

@Component({
  standalone: true,
  selector: 'jhi-demandeur-update',
  templateUrl: './demandeur-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DemandeurUpdateComponent implements OnInit {
  isSaving = false;
  demandeur: IDemandeur | null = null;
  sexeValues = Object.keys(Sexe);
  profilValues = Object.keys(Profil);

  usersSharedCollection: IUser[] = [];
  dossiersCollection: IDossier[] = [];
  elevesCollection: IEleve[] = [];
  etudiantsCollection: IEtudiant[] = [];
  professionnelsCollection: IProfessionnel[] = [];

  protected demandeurService = inject(DemandeurService);
  protected demandeurFormService = inject(DemandeurFormService);
  protected userService = inject(UserService);
  protected dossierService = inject(DossierService);
  protected eleveService = inject(EleveService);
  protected etudiantService = inject(EtudiantService);
  protected professionnelService = inject(ProfessionnelService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DemandeurFormGroup = this.demandeurFormService.createDemandeurFormGroup();

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareDossier = (o1: IDossier | null, o2: IDossier | null): boolean => this.dossierService.compareDossier(o1, o2);

  compareEleve = (o1: IEleve | null, o2: IEleve | null): boolean => this.eleveService.compareEleve(o1, o2);

  compareEtudiant = (o1: IEtudiant | null, o2: IEtudiant | null): boolean => this.etudiantService.compareEtudiant(o1, o2);

  compareProfessionnel = (o1: IProfessionnel | null, o2: IProfessionnel | null): boolean =>
    this.professionnelService.compareProfessionnel(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeur }) => {
      this.demandeur = demandeur;
      if (demandeur) {
        this.updateForm(demandeur);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandeur = this.demandeurFormService.getDemandeur(this.editForm);
    if (demandeur.id !== null) {
      this.subscribeToSaveResponse(this.demandeurService.update(demandeur));
    } else {
      this.subscribeToSaveResponse(this.demandeurService.create(demandeur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeur>>): void {
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

  protected updateForm(demandeur: IDemandeur): void {
    this.demandeur = demandeur;
    this.demandeurFormService.resetForm(this.editForm, demandeur);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, demandeur.user);
    this.dossiersCollection = this.dossierService.addDossierToCollectionIfMissing<IDossier>(this.dossiersCollection, demandeur.dossier);
    this.elevesCollection = this.eleveService.addEleveToCollectionIfMissing<IEleve>(this.elevesCollection, demandeur.eleve);
    this.etudiantsCollection = this.etudiantService.addEtudiantToCollectionIfMissing<IEtudiant>(
      this.etudiantsCollection,
      demandeur.etudiant,
    );
    this.professionnelsCollection = this.professionnelService.addProfessionnelToCollectionIfMissing<IProfessionnel>(
      this.professionnelsCollection,
      demandeur.professionnel,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.demandeur?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.dossierService
      .query({ filter: 'demandeur-is-null' })
      .pipe(map((res: HttpResponse<IDossier[]>) => res.body ?? []))
      .pipe(map((dossiers: IDossier[]) => this.dossierService.addDossierToCollectionIfMissing<IDossier>(dossiers, this.demandeur?.dossier)))
      .subscribe((dossiers: IDossier[]) => (this.dossiersCollection = dossiers));

    this.eleveService
      .query({ filter: 'demandeur-is-null' })
      .pipe(map((res: HttpResponse<IEleve[]>) => res.body ?? []))
      .pipe(map((eleves: IEleve[]) => this.eleveService.addEleveToCollectionIfMissing<IEleve>(eleves, this.demandeur?.eleve)))
      .subscribe((eleves: IEleve[]) => (this.elevesCollection = eleves));

    this.etudiantService
      .query({ filter: 'demandeur-is-null' })
      .pipe(map((res: HttpResponse<IEtudiant[]>) => res.body ?? []))
      .pipe(
        map((etudiants: IEtudiant[]) =>
          this.etudiantService.addEtudiantToCollectionIfMissing<IEtudiant>(etudiants, this.demandeur?.etudiant),
        ),
      )
      .subscribe((etudiants: IEtudiant[]) => (this.etudiantsCollection = etudiants));

    this.professionnelService
      .query({ filter: 'demandeur-is-null' })
      .pipe(map((res: HttpResponse<IProfessionnel[]>) => res.body ?? []))
      .pipe(
        map((professionnels: IProfessionnel[]) =>
          this.professionnelService.addProfessionnelToCollectionIfMissing<IProfessionnel>(professionnels, this.demandeur?.professionnel),
        ),
      )
      .subscribe((professionnels: IProfessionnel[]) => (this.professionnelsCollection = professionnels));
  }
}
