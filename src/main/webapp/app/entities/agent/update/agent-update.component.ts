import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { IServiceMFPAI } from 'app/entities/service-mfpai/service-mfpai.model';
import { ServiceMFPAIService } from 'app/entities/service-mfpai/service/service-mfpai.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { AgentService } from '../service/agent.service';
import { IAgent } from '../agent.model';
import { AgentFormService, AgentFormGroup } from './agent-form.service';

@Component({
  standalone: true,
  selector: 'jhi-agent-update',
  templateUrl: './agent-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AgentUpdateComponent implements OnInit {
  isSaving = false;
  agent: IAgent | null = null;
  sexeValues = Object.keys(Sexe);

  usersSharedCollection: IUser[] = [];
  serviceMFPAISCollection: IServiceMFPAI[] = [];

  protected agentService = inject(AgentService);
  protected agentFormService = inject(AgentFormService);
  protected userService = inject(UserService);
  protected serviceMFPAIService = inject(ServiceMFPAIService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AgentFormGroup = this.agentFormService.createAgentFormGroup();

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareServiceMFPAI = (o1: IServiceMFPAI | null, o2: IServiceMFPAI | null): boolean =>
    this.serviceMFPAIService.compareServiceMFPAI(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agent }) => {
      this.agent = agent;
      if (agent) {
        this.updateForm(agent);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agent = this.agentFormService.getAgent(this.editForm);
    if (agent.id !== null) {
      this.subscribeToSaveResponse(this.agentService.update(agent));
    } else {
      this.subscribeToSaveResponse(this.agentService.create(agent));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgent>>): void {
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

  protected updateForm(agent: IAgent): void {
    this.agent = agent;
    this.agentFormService.resetForm(this.editForm, agent);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, agent.user);
    this.serviceMFPAISCollection = this.serviceMFPAIService.addServiceMFPAIToCollectionIfMissing<IServiceMFPAI>(
      this.serviceMFPAISCollection,
      agent.serviceMFPAI,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.agent?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.serviceMFPAIService
      .query({ filter: 'agent-is-null' })
      .pipe(map((res: HttpResponse<IServiceMFPAI[]>) => res.body ?? []))
      .pipe(
        map((serviceMFPAIS: IServiceMFPAI[]) =>
          this.serviceMFPAIService.addServiceMFPAIToCollectionIfMissing<IServiceMFPAI>(serviceMFPAIS, this.agent?.serviceMFPAI),
        ),
      )
      .subscribe((serviceMFPAIS: IServiceMFPAI[]) => (this.serviceMFPAISCollection = serviceMFPAIS));
  }
}
