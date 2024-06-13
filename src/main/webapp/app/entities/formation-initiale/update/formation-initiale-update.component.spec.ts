import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';
import { FormationInitialeService } from '../service/formation-initiale.service';
import { IFormationInitiale } from '../formation-initiale.model';
import { FormationInitialeFormService } from './formation-initiale-form.service';

import { FormationInitialeUpdateComponent } from './formation-initiale-update.component';

describe('FormationInitiale Management Update Component', () => {
  let comp: FormationInitialeUpdateComponent;
  let fixture: ComponentFixture<FormationInitialeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let formationInitialeFormService: FormationInitialeFormService;
  let formationInitialeService: FormationInitialeService;
  let formationService: FormationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, FormationInitialeUpdateComponent],
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
      .overrideTemplate(FormationInitialeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FormationInitialeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    formationInitialeFormService = TestBed.inject(FormationInitialeFormService);
    formationInitialeService = TestBed.inject(FormationInitialeService);
    formationService = TestBed.inject(FormationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call formation query and add missing value', () => {
      const formationInitiale: IFormationInitiale = { id: 456 };
      const formation: IFormation = { id: 31746 };
      formationInitiale.formation = formation;

      const formationCollection: IFormation[] = [{ id: 11554 }];
      jest.spyOn(formationService, 'query').mockReturnValue(of(new HttpResponse({ body: formationCollection })));
      const expectedCollection: IFormation[] = [formation, ...formationCollection];
      jest.spyOn(formationService, 'addFormationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ formationInitiale });
      comp.ngOnInit();

      expect(formationService.query).toHaveBeenCalled();
      expect(formationService.addFormationToCollectionIfMissing).toHaveBeenCalledWith(formationCollection, formation);
      expect(comp.formationsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const formationInitiale: IFormationInitiale = { id: 456 };
      const formation: IFormation = { id: 674 };
      formationInitiale.formation = formation;

      activatedRoute.data = of({ formationInitiale });
      comp.ngOnInit();

      expect(comp.formationsCollection).toContain(formation);
      expect(comp.formationInitiale).toEqual(formationInitiale);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormationInitiale>>();
      const formationInitiale = { id: 123 };
      jest.spyOn(formationInitialeFormService, 'getFormationInitiale').mockReturnValue(formationInitiale);
      jest.spyOn(formationInitialeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formationInitiale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formationInitiale }));
      saveSubject.complete();

      // THEN
      expect(formationInitialeFormService.getFormationInitiale).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(formationInitialeService.update).toHaveBeenCalledWith(expect.objectContaining(formationInitiale));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormationInitiale>>();
      const formationInitiale = { id: 123 };
      jest.spyOn(formationInitialeFormService, 'getFormationInitiale').mockReturnValue({ id: null });
      jest.spyOn(formationInitialeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formationInitiale: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formationInitiale }));
      saveSubject.complete();

      // THEN
      expect(formationInitialeFormService.getFormationInitiale).toHaveBeenCalled();
      expect(formationInitialeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormationInitiale>>();
      const formationInitiale = { id: 123 };
      jest.spyOn(formationInitialeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formationInitiale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(formationInitialeService.update).toHaveBeenCalled();
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
