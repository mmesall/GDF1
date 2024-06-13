import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { ProfessionnelService } from 'app/entities/professionnel/service/professionnel.service';
import { IFormationContinue } from 'app/entities/formation-continue/formation-continue.model';
import { FormationContinueService } from 'app/entities/formation-continue/service/formation-continue.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { ICandidatureP } from '../candidature-p.model';
import { CandidaturePService } from '../service/candidature-p.service';
import { CandidaturePFormService } from './candidature-p-form.service';

import { CandidaturePUpdateComponent } from './candidature-p-update.component';

describe('CandidatureP Management Update Component', () => {
  let comp: CandidaturePUpdateComponent;
  let fixture: ComponentFixture<CandidaturePUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let candidaturePFormService: CandidaturePFormService;
  let candidaturePService: CandidaturePService;
  let professionnelService: ProfessionnelService;
  let formationContinueService: FormationContinueService;
  let etablissementService: EtablissementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CandidaturePUpdateComponent],
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
      .overrideTemplate(CandidaturePUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CandidaturePUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    candidaturePFormService = TestBed.inject(CandidaturePFormService);
    candidaturePService = TestBed.inject(CandidaturePService);
    professionnelService = TestBed.inject(ProfessionnelService);
    formationContinueService = TestBed.inject(FormationContinueService);
    etablissementService = TestBed.inject(EtablissementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Professionnel query and add missing value', () => {
      const candidatureP: ICandidatureP = { id: 456 };
      const professionnel: IProfessionnel = { id: 17829 };
      candidatureP.professionnel = professionnel;

      const professionnelCollection: IProfessionnel[] = [{ id: 27529 }];
      jest.spyOn(professionnelService, 'query').mockReturnValue(of(new HttpResponse({ body: professionnelCollection })));
      const additionalProfessionnels = [professionnel];
      const expectedCollection: IProfessionnel[] = [...additionalProfessionnels, ...professionnelCollection];
      jest.spyOn(professionnelService, 'addProfessionnelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ candidatureP });
      comp.ngOnInit();

      expect(professionnelService.query).toHaveBeenCalled();
      expect(professionnelService.addProfessionnelToCollectionIfMissing).toHaveBeenCalledWith(
        professionnelCollection,
        ...additionalProfessionnels.map(expect.objectContaining),
      );
      expect(comp.professionnelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FormationContinue query and add missing value', () => {
      const candidatureP: ICandidatureP = { id: 456 };
      const formationContinue: IFormationContinue = { id: 9871 };
      candidatureP.formationContinue = formationContinue;

      const formationContinueCollection: IFormationContinue[] = [{ id: 17986 }];
      jest.spyOn(formationContinueService, 'query').mockReturnValue(of(new HttpResponse({ body: formationContinueCollection })));
      const additionalFormationContinues = [formationContinue];
      const expectedCollection: IFormationContinue[] = [...additionalFormationContinues, ...formationContinueCollection];
      jest.spyOn(formationContinueService, 'addFormationContinueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ candidatureP });
      comp.ngOnInit();

      expect(formationContinueService.query).toHaveBeenCalled();
      expect(formationContinueService.addFormationContinueToCollectionIfMissing).toHaveBeenCalledWith(
        formationContinueCollection,
        ...additionalFormationContinues.map(expect.objectContaining),
      );
      expect(comp.formationContinuesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Etablissement query and add missing value', () => {
      const candidatureP: ICandidatureP = { id: 456 };
      const etablissement: IEtablissement = { id: 7357 };
      candidatureP.etablissement = etablissement;

      const etablissementCollection: IEtablissement[] = [{ id: 14680 }];
      jest.spyOn(etablissementService, 'query').mockReturnValue(of(new HttpResponse({ body: etablissementCollection })));
      const additionalEtablissements = [etablissement];
      const expectedCollection: IEtablissement[] = [...additionalEtablissements, ...etablissementCollection];
      jest.spyOn(etablissementService, 'addEtablissementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ candidatureP });
      comp.ngOnInit();

      expect(etablissementService.query).toHaveBeenCalled();
      expect(etablissementService.addEtablissementToCollectionIfMissing).toHaveBeenCalledWith(
        etablissementCollection,
        ...additionalEtablissements.map(expect.objectContaining),
      );
      expect(comp.etablissementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const candidatureP: ICandidatureP = { id: 456 };
      const professionnel: IProfessionnel = { id: 32496 };
      candidatureP.professionnel = professionnel;
      const formationContinue: IFormationContinue = { id: 6287 };
      candidatureP.formationContinue = formationContinue;
      const etablissement: IEtablissement = { id: 14147 };
      candidatureP.etablissement = etablissement;

      activatedRoute.data = of({ candidatureP });
      comp.ngOnInit();

      expect(comp.professionnelsSharedCollection).toContain(professionnel);
      expect(comp.formationContinuesSharedCollection).toContain(formationContinue);
      expect(comp.etablissementsSharedCollection).toContain(etablissement);
      expect(comp.candidatureP).toEqual(candidatureP);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICandidatureP>>();
      const candidatureP = { id: 123 };
      jest.spyOn(candidaturePFormService, 'getCandidatureP').mockReturnValue(candidatureP);
      jest.spyOn(candidaturePService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ candidatureP });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: candidatureP }));
      saveSubject.complete();

      // THEN
      expect(candidaturePFormService.getCandidatureP).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(candidaturePService.update).toHaveBeenCalledWith(expect.objectContaining(candidatureP));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICandidatureP>>();
      const candidatureP = { id: 123 };
      jest.spyOn(candidaturePFormService, 'getCandidatureP').mockReturnValue({ id: null });
      jest.spyOn(candidaturePService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ candidatureP: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: candidatureP }));
      saveSubject.complete();

      // THEN
      expect(candidaturePFormService.getCandidatureP).toHaveBeenCalled();
      expect(candidaturePService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICandidatureP>>();
      const candidatureP = { id: 123 };
      jest.spyOn(candidaturePService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ candidatureP });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(candidaturePService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProfessionnel', () => {
      it('Should forward to professionnelService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(professionnelService, 'compareProfessionnel');
        comp.compareProfessionnel(entity, entity2);
        expect(professionnelService.compareProfessionnel).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFormationContinue', () => {
      it('Should forward to formationContinueService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(formationContinueService, 'compareFormationContinue');
        comp.compareFormationContinue(entity, entity2);
        expect(formationContinueService.compareFormationContinue).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEtablissement', () => {
      it('Should forward to etablissementService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(etablissementService, 'compareEtablissement');
        comp.compareEtablissement(entity, entity2);
        expect(etablissementService.compareEtablissement).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
