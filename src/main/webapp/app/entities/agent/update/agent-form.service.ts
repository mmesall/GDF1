import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAgent, NewAgent } from '../agent.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAgent for edit and NewAgentFormGroupInput for create.
 */
type AgentFormGroupInput = IAgent | PartialWithRequiredKeyOf<NewAgent>;

type AgentFormDefaults = Pick<NewAgent, 'id'>;

type AgentFormGroupContent = {
  id: FormControl<IAgent['id'] | NewAgent['id']>;
  matricule: FormControl<IAgent['matricule']>;
  nomAgent: FormControl<IAgent['nomAgent']>;
  prenom: FormControl<IAgent['prenom']>;
  dateNaiss: FormControl<IAgent['dateNaiss']>;
  lieuNaiss: FormControl<IAgent['lieuNaiss']>;
  sexe: FormControl<IAgent['sexe']>;
  telephone: FormControl<IAgent['telephone']>;
  email: FormControl<IAgent['email']>;
  user: FormControl<IAgent['user']>;
  serviceMFPAI: FormControl<IAgent['serviceMFPAI']>;
};

export type AgentFormGroup = FormGroup<AgentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AgentFormService {
  createAgentFormGroup(agent: AgentFormGroupInput = { id: null }): AgentFormGroup {
    const agentRawValue = {
      ...this.getFormDefaults(),
      ...agent,
    };
    return new FormGroup<AgentFormGroupContent>({
      id: new FormControl(
        { value: agentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      matricule: new FormControl(agentRawValue.matricule, {
        validators: [Validators.required],
      }),
      nomAgent: new FormControl(agentRawValue.nomAgent),
      prenom: new FormControl(agentRawValue.prenom),
      dateNaiss: new FormControl(agentRawValue.dateNaiss),
      lieuNaiss: new FormControl(agentRawValue.lieuNaiss),
      sexe: new FormControl(agentRawValue.sexe),
      telephone: new FormControl(agentRawValue.telephone),
      email: new FormControl(agentRawValue.email),
      user: new FormControl(agentRawValue.user),
      serviceMFPAI: new FormControl(agentRawValue.serviceMFPAI),
    });
  }

  getAgent(form: AgentFormGroup): IAgent | NewAgent {
    return form.getRawValue() as IAgent | NewAgent;
  }

  resetForm(form: AgentFormGroup, agent: AgentFormGroupInput): void {
    const agentRawValue = { ...this.getFormDefaults(), ...agent };
    form.reset(
      {
        ...agentRawValue,
        id: { value: agentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AgentFormDefaults {
    return {
      id: null,
    };
  }
}
