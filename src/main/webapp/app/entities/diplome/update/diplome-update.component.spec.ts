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
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { ProfessionnelService } from 'app/entities/professionnel/service/professionnel.service';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { DemandeurService } from 'app/entities/demandeur/service/demandeur.service';
import { IDiplome } from '../diplome.model';
import { DiplomeService } from '../service/diplome.service';
import { DiplomeFormService } from './diplome-form.service';

import { DiplomeUpdateComponent } from './diplome-update.component';

describe('Diplome Management Update Component', () => {
  let comp: DiplomeUpdateComponent;
  let fixture: ComponentFixture<DiplomeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let diplomeFormService: DiplomeFormService;
  let diplomeService: DiplomeService;
  let eleveService: EleveService;
  let etudiantService: EtudiantService;
  let professionnelService: ProfessionnelService;
  let demandeurService: DemandeurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, DiplomeUpdateComponent],
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
      .overrideTemplate(DiplomeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DiplomeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    diplomeFormService = TestBed.inject(DiplomeFormService);
    diplomeService = TestBed.inject(DiplomeService);
    eleveService = TestBed.inject(EleveService);
    etudiantService = TestBed.inject(EtudiantService);
    professionnelService = TestBed.inject(ProfessionnelService);
    demandeurService = TestBed.inject(DemandeurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Eleve query and add missing value', () => {
      const diplome: IDiplome = { id: 456 };
      const eleve: IEleve = { id: 26747 };
      diplome.eleve = eleve;

      const eleveCollection: IEleve[] = [{ id: 15581 }];
      jest.spyOn(eleveService, 'query').mockReturnValue(of(new HttpResponse({ body: eleveCollection })));
      const additionalEleves = [eleve];
      const expectedCollection: IEleve[] = [...additionalEleves, ...eleveCollection];
      jest.spyOn(eleveService, 'addEleveToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ diplome });
      comp.ngOnInit();

      expect(eleveService.query).toHaveBeenCalled();
      expect(eleveService.addEleveToCollectionIfMissing).toHaveBeenCalledWith(
        eleveCollection,
        ...additionalEleves.map(expect.objectContaining),
      );
      expect(comp.elevesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Etudiant query and add missing value', () => {
      const diplome: IDiplome = { id: 456 };
      const etudiant: IEtudiant = { id: 16802 };
      diplome.etudiant = etudiant;

      const etudiantCollection: IEtudiant[] = [{ id: 13403 }];
      jest.spyOn(etudiantService, 'query').mockReturnValue(of(new HttpResponse({ body: etudiantCollection })));
      const additionalEtudiants = [etudiant];
      const expectedCollection: IEtudiant[] = [...additionalEtudiants, ...etudiantCollection];
      jest.spyOn(etudiantService, 'addEtudiantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ diplome });
      comp.ngOnInit();

      expect(etudiantService.query).toHaveBeenCalled();
      expect(etudiantService.addEtudiantToCollectionIfMissing).toHaveBeenCalledWith(
        etudiantCollection,
        ...additionalEtudiants.map(expect.objectContaining),
      );
      expect(comp.etudiantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Professionnel query and add missing value', () => {
      const diplome: IDiplome = { id: 456 };
      const professionnel: IProfessionnel = { id: 6532 };
      diplome.professionnel = professionnel;

      const professionnelCollection: IProfessionnel[] = [{ id: 31721 }];
      jest.spyOn(professionnelService, 'query').mockReturnValue(of(new HttpResponse({ body: professionnelCollection })));
      const additionalProfessionnels = [professionnel];
      const expectedCollection: IProfessionnel[] = [...additionalProfessionnels, ...professionnelCollection];
      jest.spyOn(professionnelService, 'addProfessionnelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ diplome });
      comp.ngOnInit();

      expect(professionnelService.query).toHaveBeenCalled();
      expect(professionnelService.addProfessionnelToCollectionIfMissing).toHaveBeenCalledWith(
        professionnelCollection,
        ...additionalProfessionnels.map(expect.objectContaining),
      );
      expect(comp.professionnelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Demandeur query and add missing value', () => {
      const diplome: IDiplome = { id: 456 };
      const demandeur: IDemandeur = { id: 18647 };
      diplome.demandeur = demandeur;

      const demandeurCollection: IDemandeur[] = [{ id: 29531 }];
      jest.spyOn(demandeurService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeurCollection })));
      const additionalDemandeurs = [demandeur];
      const expectedCollection: IDemandeur[] = [...additionalDemandeurs, ...demandeurCollection];
      jest.spyOn(demandeurService, 'addDemandeurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ diplome });
      comp.ngOnInit();

      expect(demandeurService.query).toHaveBeenCalled();
      expect(demandeurService.addDemandeurToCollectionIfMissing).toHaveBeenCalledWith(
        demandeurCollection,
        ...additionalDemandeurs.map(expect.objectContaining),
      );
      expect(comp.demandeursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const diplome: IDiplome = { id: 456 };
      const eleve: IEleve = { id: 21268 };
      diplome.eleve = eleve;
      const etudiant: IEtudiant = { id: 5862 };
      diplome.etudiant = etudiant;
      const professionnel: IProfessionnel = { id: 23717 };
      diplome.professionnel = professionnel;
      const demandeur: IDemandeur = { id: 23934 };
      diplome.demandeur = demandeur;

      activatedRoute.data = of({ diplome });
      comp.ngOnInit();

      expect(comp.elevesSharedCollection).toContain(eleve);
      expect(comp.etudiantsSharedCollection).toContain(etudiant);
      expect(comp.professionnelsSharedCollection).toContain(professionnel);
      expect(comp.demandeursSharedCollection).toContain(demandeur);
      expect(comp.diplome).toEqual(diplome);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDiplome>>();
      const diplome = { id: 123 };
      jest.spyOn(diplomeFormService, 'getDiplome').mockReturnValue(diplome);
      jest.spyOn(diplomeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diplome });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: diplome }));
      saveSubject.complete();

      // THEN
      expect(diplomeFormService.getDiplome).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(diplomeService.update).toHaveBeenCalledWith(expect.objectContaining(diplome));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDiplome>>();
      const diplome = { id: 123 };
      jest.spyOn(diplomeFormService, 'getDiplome').mockReturnValue({ id: null });
      jest.spyOn(diplomeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diplome: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: diplome }));
      saveSubject.complete();

      // THEN
      expect(diplomeFormService.getDiplome).toHaveBeenCalled();
      expect(diplomeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDiplome>>();
      const diplome = { id: 123 };
      jest.spyOn(diplomeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diplome });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(diplomeService.update).toHaveBeenCalled();
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

    describe('compareProfessionnel', () => {
      it('Should forward to professionnelService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(professionnelService, 'compareProfessionnel');
        comp.compareProfessionnel(entity, entity2);
        expect(professionnelService.compareProfessionnel).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDemandeur', () => {
      it('Should forward to demandeurService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(demandeurService, 'compareDemandeur');
        comp.compareDemandeur(entity, entity2);
        expect(demandeurService.compareDemandeur).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
