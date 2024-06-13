import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEleve } from 'app/entities/eleve/eleve.model';
import { EleveService } from 'app/entities/eleve/service/eleve.service';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { EtudiantService } from 'app/entities/etudiant/service/etudiant.service';
import { IFormationInitiale } from 'app/entities/formation-initiale/formation-initiale.model';
import { FormationInitialeService } from 'app/entities/formation-initiale/service/formation-initiale.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { ICandidatureE } from '../candidature-e.model';
import { CandidatureEService } from '../service/candidature-e.service';
import { CandidatureEFormService } from './candidature-e-form.service';

import { CandidatureEUpdateComponent } from './candidature-e-update.component';

describe('CandidatureE Management Update Component', () => {
  let comp: CandidatureEUpdateComponent;
  let fixture: ComponentFixture<CandidatureEUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let candidatureEFormService: CandidatureEFormService;
  let candidatureEService: CandidatureEService;
  let eleveService: EleveService;
  let etudiantService: EtudiantService;
  let formationInitialeService: FormationInitialeService;
  let etablissementService: EtablissementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CandidatureEUpdateComponent],
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
      .overrideTemplate(CandidatureEUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CandidatureEUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    candidatureEFormService = TestBed.inject(CandidatureEFormService);
    candidatureEService = TestBed.inject(CandidatureEService);
    eleveService = TestBed.inject(EleveService);
    etudiantService = TestBed.inject(EtudiantService);
    formationInitialeService = TestBed.inject(FormationInitialeService);
    etablissementService = TestBed.inject(EtablissementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Eleve query and add missing value', () => {
      const candidatureE: ICandidatureE = { id: 456 };
      const eleve: IEleve = { id: 832 };
      candidatureE.eleve = eleve;

      const eleveCollection: IEleve[] = [{ id: 2690 }];
      jest.spyOn(eleveService, 'query').mockReturnValue(of(new HttpResponse({ body: eleveCollection })));
      const additionalEleves = [eleve];
      const expectedCollection: IEleve[] = [...additionalEleves, ...eleveCollection];
      jest.spyOn(eleveService, 'addEleveToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ candidatureE });
      comp.ngOnInit();

      expect(eleveService.query).toHaveBeenCalled();
      expect(eleveService.addEleveToCollectionIfMissing).toHaveBeenCalledWith(
        eleveCollection,
        ...additionalEleves.map(expect.objectContaining),
      );
      expect(comp.elevesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Etudiant query and add missing value', () => {
      const candidatureE: ICandidatureE = { id: 456 };
      const etudiant: IEtudiant = { id: 18595 };
      candidatureE.etudiant = etudiant;

      const etudiantCollection: IEtudiant[] = [{ id: 17210 }];
      jest.spyOn(etudiantService, 'query').mockReturnValue(of(new HttpResponse({ body: etudiantCollection })));
      const additionalEtudiants = [etudiant];
      const expectedCollection: IEtudiant[] = [...additionalEtudiants, ...etudiantCollection];
      jest.spyOn(etudiantService, 'addEtudiantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ candidatureE });
      comp.ngOnInit();

      expect(etudiantService.query).toHaveBeenCalled();
      expect(etudiantService.addEtudiantToCollectionIfMissing).toHaveBeenCalledWith(
        etudiantCollection,
        ...additionalEtudiants.map(expect.objectContaining),
      );
      expect(comp.etudiantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FormationInitiale query and add missing value', () => {
      const candidatureE: ICandidatureE = { id: 456 };
      const formationInitiale: IFormationInitiale = { id: 8677 };
      candidatureE.formationInitiale = formationInitiale;

      const formationInitialeCollection: IFormationInitiale[] = [{ id: 24328 }];
      jest.spyOn(formationInitialeService, 'query').mockReturnValue(of(new HttpResponse({ body: formationInitialeCollection })));
      const additionalFormationInitiales = [formationInitiale];
      const expectedCollection: IFormationInitiale[] = [...additionalFormationInitiales, ...formationInitialeCollection];
      jest.spyOn(formationInitialeService, 'addFormationInitialeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ candidatureE });
      comp.ngOnInit();

      expect(formationInitialeService.query).toHaveBeenCalled();
      expect(formationInitialeService.addFormationInitialeToCollectionIfMissing).toHaveBeenCalledWith(
        formationInitialeCollection,
        ...additionalFormationInitiales.map(expect.objectContaining),
      );
      expect(comp.formationInitialesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Etablissement query and add missing value', () => {
      const candidatureE: ICandidatureE = { id: 456 };
      const etablissement: IEtablissement = { id: 13513 };
      candidatureE.etablissement = etablissement;

      const etablissementCollection: IEtablissement[] = [{ id: 27465 }];
      jest.spyOn(etablissementService, 'query').mockReturnValue(of(new HttpResponse({ body: etablissementCollection })));
      const additionalEtablissements = [etablissement];
      const expectedCollection: IEtablissement[] = [...additionalEtablissements, ...etablissementCollection];
      jest.spyOn(etablissementService, 'addEtablissementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ candidatureE });
      comp.ngOnInit();

      expect(etablissementService.query).toHaveBeenCalled();
      expect(etablissementService.addEtablissementToCollectionIfMissing).toHaveBeenCalledWith(
        etablissementCollection,
        ...additionalEtablissements.map(expect.objectContaining),
      );
      expect(comp.etablissementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const candidatureE: ICandidatureE = { id: 456 };
      const eleve: IEleve = { id: 17942 };
      candidatureE.eleve = eleve;
      const etudiant: IEtudiant = { id: 15279 };
      candidatureE.etudiant = etudiant;
      const formationInitiale: IFormationInitiale = { id: 14066 };
      candidatureE.formationInitiale = formationInitiale;
      const etablissement: IEtablissement = { id: 8886 };
      candidatureE.etablissement = etablissement;

      activatedRoute.data = of({ candidatureE });
      comp.ngOnInit();

      expect(comp.elevesSharedCollection).toContain(eleve);
      expect(comp.etudiantsSharedCollection).toContain(etudiant);
      expect(comp.formationInitialesSharedCollection).toContain(formationInitiale);
      expect(comp.etablissementsSharedCollection).toContain(etablissement);
      expect(comp.candidatureE).toEqual(candidatureE);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICandidatureE>>();
      const candidatureE = { id: 123 };
      jest.spyOn(candidatureEFormService, 'getCandidatureE').mockReturnValue(candidatureE);
      jest.spyOn(candidatureEService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ candidatureE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: candidatureE }));
      saveSubject.complete();

      // THEN
      expect(candidatureEFormService.getCandidatureE).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(candidatureEService.update).toHaveBeenCalledWith(expect.objectContaining(candidatureE));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICandidatureE>>();
      const candidatureE = { id: 123 };
      jest.spyOn(candidatureEFormService, 'getCandidatureE').mockReturnValue({ id: null });
      jest.spyOn(candidatureEService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ candidatureE: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: candidatureE }));
      saveSubject.complete();

      // THEN
      expect(candidatureEFormService.getCandidatureE).toHaveBeenCalled();
      expect(candidatureEService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICandidatureE>>();
      const candidatureE = { id: 123 };
      jest.spyOn(candidatureEService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ candidatureE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(candidatureEService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEleve', () => {
      it('Should forward to eleveService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(eleveService, 'compareEleve');
        comp.compareEleve(entity, entity2);
        expect(eleveService.compareEleve).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEtudiant', () => {
      it('Should forward to etudiantService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(etudiantService, 'compareEtudiant');
        comp.compareEtudiant(entity, entity2);
        expect(etudiantService.compareEtudiant).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFormationInitiale', () => {
      it('Should forward to formationInitialeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(formationInitialeService, 'compareFormationInitiale');
        comp.compareFormationInitiale(entity, entity2);
        expect(formationInitialeService.compareFormationInitiale).toHaveBeenCalledWith(entity, entity2);
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
