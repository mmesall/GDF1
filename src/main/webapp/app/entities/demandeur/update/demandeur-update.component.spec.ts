import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { EleveService } from 'app/entities/eleve/service/eleve.service';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { EtudiantService } from 'app/entities/etudiant/service/etudiant.service';
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { ProfessionnelService } from 'app/entities/professionnel/service/professionnel.service';
import { IDemandeur } from '../demandeur.model';
import { DemandeurService } from '../service/demandeur.service';
import { DemandeurFormService } from './demandeur-form.service';

import { DemandeurUpdateComponent } from './demandeur-update.component';

describe('Demandeur Management Update Component', () => {
  let comp: DemandeurUpdateComponent;
  let fixture: ComponentFixture<DemandeurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let demandeurFormService: DemandeurFormService;
  let demandeurService: DemandeurService;
  let userService: UserService;
  let dossierService: DossierService;
  let eleveService: EleveService;
  let etudiantService: EtudiantService;
  let professionnelService: ProfessionnelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, DemandeurUpdateComponent],
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
      .overrideTemplate(DemandeurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DemandeurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    demandeurFormService = TestBed.inject(DemandeurFormService);
    demandeurService = TestBed.inject(DemandeurService);
    userService = TestBed.inject(UserService);
    dossierService = TestBed.inject(DossierService);
    eleveService = TestBed.inject(EleveService);
    etudiantService = TestBed.inject(EtudiantService);
    professionnelService = TestBed.inject(ProfessionnelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const demandeur: IDemandeur = { id: 456 };
      const user: IUser = { id: 11147 };
      demandeur.user = user;

      const userCollection: IUser[] = [{ id: 11507 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call dossier query and add missing value', () => {
      const demandeur: IDemandeur = { id: 456 };
      const dossier: IDossier = { id: 5024 };
      demandeur.dossier = dossier;

      const dossierCollection: IDossier[] = [{ id: 11528 }];
      jest.spyOn(dossierService, 'query').mockReturnValue(of(new HttpResponse({ body: dossierCollection })));
      const expectedCollection: IDossier[] = [dossier, ...dossierCollection];
      jest.spyOn(dossierService, 'addDossierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(dossierService.query).toHaveBeenCalled();
      expect(dossierService.addDossierToCollectionIfMissing).toHaveBeenCalledWith(dossierCollection, dossier);
      expect(comp.dossiersCollection).toEqual(expectedCollection);
    });

    it('Should call eleve query and add missing value', () => {
      const demandeur: IDemandeur = { id: 456 };
      const eleve: IEleve = { id: 25082 };
      demandeur.eleve = eleve;

      const eleveCollection: IEleve[] = [{ id: 9355 }];
      jest.spyOn(eleveService, 'query').mockReturnValue(of(new HttpResponse({ body: eleveCollection })));
      const expectedCollection: IEleve[] = [eleve, ...eleveCollection];
      jest.spyOn(eleveService, 'addEleveToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(eleveService.query).toHaveBeenCalled();
      expect(eleveService.addEleveToCollectionIfMissing).toHaveBeenCalledWith(eleveCollection, eleve);
      expect(comp.elevesCollection).toEqual(expectedCollection);
    });

    it('Should call etudiant query and add missing value', () => {
      const demandeur: IDemandeur = { id: 456 };
      const etudiant: IEtudiant = { id: 3794 };
      demandeur.etudiant = etudiant;

      const etudiantCollection: IEtudiant[] = [{ id: 14795 }];
      jest.spyOn(etudiantService, 'query').mockReturnValue(of(new HttpResponse({ body: etudiantCollection })));
      const expectedCollection: IEtudiant[] = [etudiant, ...etudiantCollection];
      jest.spyOn(etudiantService, 'addEtudiantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(etudiantService.query).toHaveBeenCalled();
      expect(etudiantService.addEtudiantToCollectionIfMissing).toHaveBeenCalledWith(etudiantCollection, etudiant);
      expect(comp.etudiantsCollection).toEqual(expectedCollection);
    });

    it('Should call professionnel query and add missing value', () => {
      const demandeur: IDemandeur = { id: 456 };
      const professionnel: IProfessionnel = { id: 20471 };
      demandeur.professionnel = professionnel;

      const professionnelCollection: IProfessionnel[] = [{ id: 30557 }];
      jest.spyOn(professionnelService, 'query').mockReturnValue(of(new HttpResponse({ body: professionnelCollection })));
      const expectedCollection: IProfessionnel[] = [professionnel, ...professionnelCollection];
      jest.spyOn(professionnelService, 'addProfessionnelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(professionnelService.query).toHaveBeenCalled();
      expect(professionnelService.addProfessionnelToCollectionIfMissing).toHaveBeenCalledWith(professionnelCollection, professionnel);
      expect(comp.professionnelsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const demandeur: IDemandeur = { id: 456 };
      const user: IUser = { id: 21286 };
      demandeur.user = user;
      const dossier: IDossier = { id: 19307 };
      demandeur.dossier = dossier;
      const eleve: IEleve = { id: 12356 };
      demandeur.eleve = eleve;
      const etudiant: IEtudiant = { id: 16001 };
      demandeur.etudiant = etudiant;
      const professionnel: IProfessionnel = { id: 9839 };
      demandeur.professionnel = professionnel;

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.dossiersCollection).toContain(dossier);
      expect(comp.elevesCollection).toContain(eleve);
      expect(comp.etudiantsCollection).toContain(etudiant);
      expect(comp.professionnelsCollection).toContain(professionnel);
      expect(comp.demandeur).toEqual(demandeur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeur>>();
      const demandeur = { id: 123 };
      jest.spyOn(demandeurFormService, 'getDemandeur').mockReturnValue(demandeur);
      jest.spyOn(demandeurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeur }));
      saveSubject.complete();

      // THEN
      expect(demandeurFormService.getDemandeur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(demandeurService.update).toHaveBeenCalledWith(expect.objectContaining(demandeur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeur>>();
      const demandeur = { id: 123 };
      jest.spyOn(demandeurFormService, 'getDemandeur').mockReturnValue({ id: null });
      jest.spyOn(demandeurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeur }));
      saveSubject.complete();

      // THEN
      expect(demandeurFormService.getDemandeur).toHaveBeenCalled();
      expect(demandeurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeur>>();
      const demandeur = { id: 123 };
      jest.spyOn(demandeurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(demandeurService.update).toHaveBeenCalled();
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

    describe('compareDossier', () => {
      it('Should forward to dossierService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(dossierService, 'compareDossier');
        comp.compareDossier(entity, entity2);
        expect(dossierService.compareDossier).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
  });
});
