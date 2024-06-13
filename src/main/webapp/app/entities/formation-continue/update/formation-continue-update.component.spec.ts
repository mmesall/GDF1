import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';
import { FormationContinueService } from '../service/formation-continue.service';
import { IFormationContinue } from '../formation-continue.model';
import { FormationContinueFormService } from './formation-continue-form.service';

import { FormationContinueUpdateComponent } from './formation-continue-update.component';

describe('FormationContinue Management Update Component', () => {
  let comp: FormationContinueUpdateComponent;
  let fixture: ComponentFixture<FormationContinueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let formationContinueFormService: FormationContinueFormService;
  let formationContinueService: FormationContinueService;
  let formationService: FormationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, FormationContinueUpdateComponent],
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
      .overrideTemplate(FormationContinueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FormationContinueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    formationContinueFormService = TestBed.inject(FormationContinueFormService);
    formationContinueService = TestBed.inject(FormationContinueService);
    formationService = TestBed.inject(FormationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call formation query and add missing value', () => {
      const formationContinue: IFormationContinue = { id: 456 };
      const formation: IFormation = { id: 29586 };
      formationContinue.formation = formation;

      const formationCollection: IFormation[] = [{ id: 2410 }];
      jest.spyOn(formationService, 'query').mockReturnValue(of(new HttpResponse({ body: formationCollection })));
      const expectedCollection: IFormation[] = [formation, ...formationCollection];
      jest.spyOn(formationService, 'addFormationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ formationContinue });
      comp.ngOnInit();

      expect(formationService.query).toHaveBeenCalled();
      expect(formationService.addFormationToCollectionIfMissing).toHaveBeenCalledWith(formationCollection, formation);
      expect(comp.formationsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const formationContinue: IFormationContinue = { id: 456 };
      const formation: IFormation = { id: 15057 };
      formationContinue.formation = formation;

      activatedRoute.data = of({ formationContinue });
      comp.ngOnInit();

      expect(comp.formationsCollection).toContain(formation);
      expect(comp.formationContinue).toEqual(formationContinue);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormationContinue>>();
      const formationContinue = { id: 123 };
      jest.spyOn(formationContinueFormService, 'getFormationContinue').mockReturnValue(formationContinue);
      jest.spyOn(formationContinueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formationContinue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formationContinue }));
      saveSubject.complete();

      // THEN
      expect(formationContinueFormService.getFormationContinue).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(formationContinueService.update).toHaveBeenCalledWith(expect.objectContaining(formationContinue));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormationContinue>>();
      const formationContinue = { id: 123 };
      jest.spyOn(formationContinueFormService, 'getFormationContinue').mockReturnValue({ id: null });
      jest.spyOn(formationContinueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formationContinue: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formationContinue }));
      saveSubject.complete();

      // THEN
      expect(formationContinueFormService.getFormationContinue).toHaveBeenCalled();
      expect(formationContinueService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormationContinue>>();
      const formationContinue = { id: 123 };
      jest.spyOn(formationContinueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formationContinue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(formationContinueService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFormation', () => {
      it('Should forward to formationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(formationService, 'compareFormation');
        comp.compareFormation(entity, entity2);
        expect(formationService.compareFormation).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
