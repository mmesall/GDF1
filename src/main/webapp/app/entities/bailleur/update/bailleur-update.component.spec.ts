import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { BailleurService } from '../service/bailleur.service';
import { IBailleur } from '../bailleur.model';
import { BailleurFormService } from './bailleur-form.service';

import { BailleurUpdateComponent } from './bailleur-update.component';

describe('Bailleur Management Update Component', () => {
  let comp: BailleurUpdateComponent;
  let fixture: ComponentFixture<BailleurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bailleurFormService: BailleurFormService;
  let bailleurService: BailleurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, BailleurUpdateComponent],
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
      .overrideTemplate(BailleurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BailleurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bailleurFormService = TestBed.inject(BailleurFormService);
    bailleurService = TestBed.inject(BailleurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const bailleur: IBailleur = { id: 456 };

      activatedRoute.data = of({ bailleur });
      comp.ngOnInit();

      expect(comp.bailleur).toEqual(bailleur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBailleur>>();
      const bailleur = { id: 123 };
      jest.spyOn(bailleurFormService, 'getBailleur').mockReturnValue(bailleur);
      jest.spyOn(bailleurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bailleur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bailleur }));
      saveSubject.complete();

      // THEN
      expect(bailleurFormService.getBailleur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bailleurService.update).toHaveBeenCalledWith(expect.objectContaining(bailleur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBailleur>>();
      const bailleur = { id: 123 };
      jest.spyOn(bailleurFormService, 'getBailleur').mockReturnValue({ id: null });
      jest.spyOn(bailleurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bailleur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bailleur }));
      saveSubject.complete();

      // THEN
      expect(bailleurFormService.getBailleur).toHaveBeenCalled();
      expect(bailleurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBailleur>>();
      const bailleur = { id: 123 };
      jest.spyOn(bailleurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bailleur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bailleurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
