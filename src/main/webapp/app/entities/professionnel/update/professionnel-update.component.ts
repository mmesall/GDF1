import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { NomRegion } from 'app/entities/enumerations/nom-region.model';
import { NomDepartement } from 'app/entities/enumerations/nom-departement.model';
import { ProfessionnelService } from '../service/professionnel.service';
import { IProfessionnel } from '../professionnel.model';
import { ProfessionnelFormService, ProfessionnelFormGroup } from './professionnel-form.service';

@Component({
  standalone: true,
  selector: 'jhi-professionnel-update',
  templateUrl: './professionnel-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProfessionnelUpdateComponent implements OnInit {
  isSaving = false;
  professionnel: IProfessionnel | null = null;
  sexeValues = Object.keys(Sexe);
  nomRegionValues = Object.keys(NomRegion);
  nomDepartementValues = Object.keys(NomDepartement);

  usersSharedCollection: IUser[] = [];

  protected professionnelService = inject(ProfessionnelService);
  protected professionnelFormService = inject(ProfessionnelFormService);
  protected userService = inject(UserService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ProfessionnelFormGroup = this.professionnelFormService.createProfessionnelFormGroup();

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ professionnel }) => {
      this.professionnel = professionnel;
      if (professionnel) {
        this.updateForm(professionnel);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const professionnel = this.professionnelFormService.getProfessionnel(this.editForm);
    if (professionnel.id !== null) {
      this.subscribeToSaveResponse(this.professionnelService.update(professionnel));
    } else {
      this.subscribeToSaveResponse(this.professionnelService.create(professionnel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfessionnel>>): void {
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

  protected updateForm(professionnel: IProfessionnel): void {
    this.professionnel = professionnel;
    this.professionnelFormService.resetForm(this.editForm, professionnel);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, professionnel.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.professionnel?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
