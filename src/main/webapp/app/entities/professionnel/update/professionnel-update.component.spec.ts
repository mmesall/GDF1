import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { ProfessionnelService } from '../service/professionnel.service';
import { IProfessionnel } from '../professionnel.model';
import { ProfessionnelFormService } from './professionnel-form.service';

import { ProfessionnelUpdateComponent } from './professionnel-update.component';

describe('Professionnel Management Update Component', () => {
  let comp: ProfessionnelUpdateComponent;
  let fixture: ComponentFixture<ProfessionnelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let professionnelFormService: ProfessionnelFormService;
  let professionnelService: ProfessionnelService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ProfessionnelUpdateComponent],
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
      .overrideTemplate(ProfessionnelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProfessionnelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    professionnelFormService = TestBed.inject(ProfessionnelFormService);
    professionnelService = TestBed.inject(ProfessionnelService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const professionnel: IProfessionnel = { id: 456 };
      const user: IUser = { id: 24990 };
      professionnel.user = user;

      const userCollection: IUser[] = [{ id: 2427 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ professionnel });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const professionnel: IProfessionnel = { id: 456 };
      const user: IUser = { id: 10562 };
      professionnel.user = user;

      activatedRoute.data = of({ professionnel });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.professionnel).toEqual(professionnel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfessionnel>>();
      const professionnel = { id: 123 };
      jest.spyOn(professionnelFormService, 'getProfessionnel').mockReturnValue(professionnel);
      jest.spyOn(professionnelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ professionnel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: professionnel }));
      saveSubject.complete();

      // THEN
      expect(professionnelFormService.getProfessionnel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(professionnelService.update).toHaveBeenCalledWith(expect.objectContaining(professionnel));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfessionnel>>();
      const professionnel = { id: 123 };
      jest.spyOn(professionnelFormService, 'getProfessionnel').mockReturnValue({ id: null });
      jest.spyOn(professionnelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ professionnel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: professionnel }));
      saveSubject.complete();

      // THEN
      expect(professionnelFormService.getProfessionnel).toHaveBeenCalled();
      expect(professionnelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfessionnel>>();
      const professionnel = { id: 123 };
      jest.spyOn(professionnelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ professionnel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(professionnelService.update).toHaveBeenCalled();
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
  });
});
