import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ServiceMFPAIService } from '../service/service-mfpai.service';
import { IServiceMFPAI } from '../service-mfpai.model';
import { ServiceMFPAIFormService } from './service-mfpai-form.service';

import { ServiceMFPAIUpdateComponent } from './service-mfpai-update.component';

describe('ServiceMFPAI Management Update Component', () => {
  let comp: ServiceMFPAIUpdateComponent;
  let fixture: ComponentFixture<ServiceMFPAIUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let serviceMFPAIFormService: ServiceMFPAIFormService;
  let serviceMFPAIService: ServiceMFPAIService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ServiceMFPAIUpdateComponent],
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
      .overrideTemplate(ServiceMFPAIUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServiceMFPAIUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    serviceMFPAIFormService = TestBed.inject(ServiceMFPAIFormService);
    serviceMFPAIService = TestBed.inject(ServiceMFPAIService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const serviceMFPAI: IServiceMFPAI = { id: 456 };

      activatedRoute.data = of({ serviceMFPAI });
      comp.ngOnInit();

      expect(comp.serviceMFPAI).toEqual(serviceMFPAI);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServiceMFPAI>>();
      const serviceMFPAI = { id: 123 };
      jest.spyOn(serviceMFPAIFormService, 'getServiceMFPAI').mockReturnValue(serviceMFPAI);
      jest.spyOn(serviceMFPAIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serviceMFPAI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: serviceMFPAI }));
      saveSubject.complete();

      // THEN
      expect(serviceMFPAIFormService.getServiceMFPAI).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(serviceMFPAIService.update).toHaveBeenCalledWith(expect.objectContaining(serviceMFPAI));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServiceMFPAI>>();
      const serviceMFPAI = { id: 123 };
      jest.spyOn(serviceMFPAIFormService, 'getServiceMFPAI').mockReturnValue({ id: null });
      jest.spyOn(serviceMFPAIService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serviceMFPAI: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: serviceMFPAI }));
      saveSubject.complete();

      // THEN
      expect(serviceMFPAIFormService.getServiceMFPAI).toHaveBeenCalled();
      expect(serviceMFPAIService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServiceMFPAI>>();
      const serviceMFPAI = { id: 123 };
      jest.spyOn(serviceMFPAIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serviceMFPAI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(serviceMFPAIService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
