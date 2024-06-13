import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';
import { IBailleur } from 'app/entities/bailleur/bailleur.model';
import { BailleurService } from 'app/entities/bailleur/service/bailleur.service';
import { IPriseEnCharge } from '../prise-en-charge.model';
import { PriseEnChargeService } from '../service/prise-en-charge.service';
import { PriseEnChargeFormService } from './prise-en-charge-form.service';

import { PriseEnChargeUpdateComponent } from './prise-en-charge-update.component';

describe('PriseEnCharge Management Update Component', () => {
  let comp: PriseEnChargeUpdateComponent;
  let fixture: ComponentFixture<PriseEnChargeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let priseEnChargeFormService: PriseEnChargeFormService;
  let priseEnChargeService: PriseEnChargeService;
  let formationService: FormationService;
  let bailleurService: BailleurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, PriseEnChargeUpdateComponent],
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
      .overrideTemplate(PriseEnChargeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PriseEnChargeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    priseEnChargeFormService = TestBed.inject(PriseEnChargeFormService);
    priseEnChargeService = TestBed.inject(PriseEnChargeService);
    formationService = TestBed.inject(FormationService);
    bailleurService = TestBed.inject(BailleurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call formation query and add missing value', () => {
      const priseEnCharge: IPriseEnCharge = { id: 456 };
      const formation: IFormation = { id: 3493 };
      priseEnCharge.formation = formation;

      const formationCollection: IFormation[] = [{ id: 15979 }];
      jest.spyOn(formationService, 'query').mockReturnValue(of(new HttpResponse({ body: formationCollection })));
      const expectedCollection: IFormation[] = [formation, ...formationCollection];
      jest.spyOn(formationService, 'addFormationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ priseEnCharge });
      comp.ngOnInit();

      expect(formationService.query).toHaveBeenCalled();
      expect(formationService.addFormationToCollectionIfMissing).toHaveBeenCalledWith(formationCollection, formation);
      expect(comp.formationsCollection).toEqual(expectedCollection);
    });

    it('Should call Bailleur query and add missing value', () => {
      const priseEnCharge: IPriseEnCharge = { id: 456 };
      const bailleur: IBailleur = { id: 7842 };
      priseEnCharge.bailleur = bailleur;

      const bailleurCollection: IBailleur[] = [{ id: 22947 }];
      jest.spyOn(bailleurService, 'query').mockReturnValue(of(new HttpResponse({ body: bailleurCollection })));
      const additionalBailleurs = [bailleur];
      const expectedCollection: IBailleur[] = [...additionalBailleurs, ...bailleurCollection];
      jest.spyOn(bailleurService, 'addBailleurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ priseEnCharge });
      comp.ngOnInit();

      expect(bailleurService.query).toHaveBeenCalled();
      expect(bailleurService.addBailleurToCollectionIfMissing).toHaveBeenCalledWith(
        bailleurCollection,
        ...additionalBailleurs.map(expect.objectContaining),
      );
      expect(comp.bailleursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const priseEnCharge: IPriseEnCharge = { id: 456 };
      const formation: IFormation = { id: 13407 };
      priseEnCharge.formation = formation;
      const bailleur: IBailleur = { id: 23070 };
      priseEnCharge.bailleur = bailleur;

      activatedRoute.data = of({ priseEnCharge });
      comp.ngOnInit();

      expect(comp.formationsCollection).toContain(formation);
      expect(comp.bailleursSharedCollection).toContain(bailleur);
      expect(comp.priseEnCharge).toEqual(priseEnCharge);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPriseEnCharge>>();
      const priseEnCharge = { id: 123 };
      jest.spyOn(priseEnChargeFormService, 'getPriseEnCharge').mockReturnValue(priseEnCharge);
      jest.spyOn(priseEnChargeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ priseEnCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: priseEnCharge }));
      saveSubject.complete();

      // THEN
      expect(priseEnChargeFormService.getPriseEnCharge).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(priseEnChargeService.update).toHaveBeenCalledWith(expect.objectContaining(priseEnCharge));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPriseEnCharge>>();
      const priseEnCharge = { id: 123 };
      jest.spyOn(priseEnChargeFormService, 'getPriseEnCharge').mockReturnValue({ id: null });
      jest.spyOn(priseEnChargeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ priseEnCharge: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: priseEnCharge }));
      saveSubject.complete();

      // THEN
      expect(priseEnChargeFormService.getPriseEnCharge).toHaveBeenCalled();
      expect(priseEnChargeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPriseEnCharge>>();
      const priseEnCharge = { id: 123 };
      jest.spyOn(priseEnChargeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ priseEnCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(priseEnChargeService.update).toHaveBeenCalled();
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

    describe('compareBailleur', () => {
      it('Should forward to bailleurService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(bailleurService, 'compareBailleur');
        comp.compareBailleur(entity, entity2);
        expect(bailleurService.compareBailleur).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
