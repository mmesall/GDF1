import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';
import { ConcoursService } from '../service/concours.service';
import { IConcours } from '../concours.model';
import { ConcoursFormService } from './concours-form.service';

import { ConcoursUpdateComponent } from './concours-update.component';

describe('Concours Management Update Component', () => {
  let comp: ConcoursUpdateComponent;
  let fixture: ComponentFixture<ConcoursUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let concoursFormService: ConcoursFormService;
  let concoursService: ConcoursService;
  let formationService: FormationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ConcoursUpdateComponent],
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
      .overrideTemplate(ConcoursUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConcoursUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    concoursFormService = TestBed.inject(ConcoursFormService);
    concoursService = TestBed.inject(ConcoursService);
    formationService = TestBed.inject(FormationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call formation query and add missing value', () => {
      const concours: IConcours = { id: 456 };
      const formation: IFormation = { id: 21517 };
      concours.formation = formation;

      const formationCollection: IFormation[] = [{ id: 22189 }];
      jest.spyOn(formationService, 'query').mockReturnValue(of(new HttpResponse({ body: formationCollection })));
      const expectedCollection: IFormation[] = [formation, ...formationCollection];
      jest.spyOn(formationService, 'addFormationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ concours });
      comp.ngOnInit();

      expect(formationService.query).toHaveBeenCalled();
      expect(formationService.addFormationToCollectionIfMissing).toHaveBeenCalledWith(formationCollection, formation);
      expect(comp.formationsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const concours: IConcours = { id: 456 };
      const formation: IFormation = { id: 26472 };
      concours.formation = formation;

      activatedRoute.data = of({ concours });
      comp.ngOnInit();

      expect(comp.formationsCollection).toContain(formation);
      expect(comp.concours).toEqual(concours);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConcours>>();
      const concours = { id: 123 };
      jest.spyOn(concoursFormService, 'getConcours').mockReturnValue(concours);
      jest.spyOn(concoursService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ concours });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: concours }));
      saveSubject.complete();

      // THEN
      expect(concoursFormService.getConcours).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(concoursService.update).toHaveBeenCalledWith(expect.objectContaining(concours));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConcours>>();
      const concours = { id: 123 };
      jest.spyOn(concoursFormService, 'getConcours').mockReturnValue({ id: null });
      jest.spyOn(concoursService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ concours: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: concours }));
      saveSubject.complete();

      // THEN
      expect(concoursFormService.getConcours).toHaveBeenCalled();
      expect(concoursService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConcours>>();
      const concours = { id: 123 };
      jest.spyOn(concoursService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ concours });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(concoursService.update).toHaveBeenCalled();
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
