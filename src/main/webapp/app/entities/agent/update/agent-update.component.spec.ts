import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { IServiceMFPAI } from 'app/entities/service-mfpai/service-mfpai.model';
import { ServiceMFPAIService } from 'app/entities/service-mfpai/service/service-mfpai.service';
import { IAgent } from '../agent.model';
import { AgentService } from '../service/agent.service';
import { AgentFormService } from './agent-form.service';

import { AgentUpdateComponent } from './agent-update.component';

describe('Agent Management Update Component', () => {
  let comp: AgentUpdateComponent;
  let fixture: ComponentFixture<AgentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let agentFormService: AgentFormService;
  let agentService: AgentService;
  let userService: UserService;
  let serviceMFPAIService: ServiceMFPAIService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, AgentUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AgentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AgentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    agentFormService = TestBed.inject(AgentFormService);
    agentService = TestBed.inject(AgentService);
    userService = TestBed.inject(UserService);
    serviceMFPAIService = TestBed.inject(ServiceMFPAIService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const agent: IAgent = { id: 456 };
      const user: IUser = { id: 16991 };
      agent.user = user;

      const userCollection: IUser[] = [{ id: 18826 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agent });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call serviceMFPAI query and add missing value', () => {
      const agent: IAgent = { id: 456 };
      const serviceMFPAI: IServiceMFPAI = { id: 20505 };
      agent.serviceMFPAI = serviceMFPAI;

      const serviceMFPAICollection: IServiceMFPAI[] = [{ id: 32444 }];
      jest.spyOn(serviceMFPAIService, 'query').mockReturnValue(of(new HttpResponse({ body: serviceMFPAICollection })));
      const expectedCollection: IServiceMFPAI[] = [serviceMFPAI, ...serviceMFPAICollection];
      jest.spyOn(serviceMFPAIService, 'addServiceMFPAIToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agent });
      comp.ngOnInit();

      expect(serviceMFPAIService.query).toHaveBeenCalled();
      expect(serviceMFPAIService.addServiceMFPAIToCollectionIfMissing).toHaveBeenCalledWith(serviceMFPAICollection, serviceMFPAI);
      expect(comp.serviceMFPAISCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const agent: IAgent = { id: 456 };
      const user: IUser = { id: 18302 };
      agent.user = user;
      const serviceMFPAI: IServiceMFPAI = { id: 810 };
      agent.serviceMFPAI = serviceMFPAI;

      activatedRoute.data = of({ agent });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.serviceMFPAISCollection).toContain(serviceMFPAI);
      expect(comp.agent).toEqual(agent);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgent>>();
      const agent = { id: 123 };
      jest.spyOn(agentFormService, 'getAgent').mockReturnValue(agent);
      jest.spyOn(agentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agent }));
      saveSubject.complete();

      // THEN
      expect(agentFormService.getAgent).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(agentService.update).toHaveBeenCalledWith(expect.objectContaining(agent));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgent>>();
      const agent = { id: 123 };
      jest.spyOn(agentFormService, 'getAgent').mockReturnValue({ id: null });
      jest.spyOn(agentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agent: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agent }));
      saveSubject.complete();

      // THEN
      expect(agentFormService.getAgent).toHaveBeenCalled();
      expect(agentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgent>>();
      const agent = { id: 123 };
      jest.spyOn(agentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(agentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareServiceMFPAI', () => {
      it('Should forward to serviceMFPAIService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(serviceMFPAIService, 'compareServiceMFPAI');
        comp.compareServiceMFPAI(entity, entity2);
        expect(serviceMFPAIService.compareServiceMFPAI).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
