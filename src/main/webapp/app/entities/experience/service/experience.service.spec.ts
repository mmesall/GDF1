import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IExperience } from '../experience.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../experience.test-samples';

import { ExperienceService, RestExperience } from './experience.service';

const requireRestSample: RestExperience = {
  ...sampleWithRequiredData,
  dateDebut: sampleWithRequiredData.dateDebut?.format(DATE_FORMAT),
  dateFin: sampleWithRequiredData.dateFin?.format(DATE_FORMAT),
};

describe('Experience Service', () => {
  let service: ExperienceService;
  let httpMock: HttpTestingController;
  let expectedResult: IExperience | IExperience[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ExperienceService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Experience', () => {
      const experience = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(experience).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Experience', () => {
      const experience = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(experience).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Experience', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Experience', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Experience', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addExperienceToCollectionIfMissing', () => {
      it('should add a Experience to an empty array', () => {
        const experience: IExperience = sampleWithRequiredData;
        expectedResult = service.addExperienceToCollectionIfMissing([], experience);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(experience);
      });

      it('should not add a Experience to an array that contains it', () => {
        const experience: IExperience = sampleWithRequiredData;
        const experienceCollection: IExperience[] = [
          {
            ...experience,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addExperienceToCollectionIfMissing(experienceCollection, experience);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Experience to an array that doesn't contain it", () => {
        const experience: IExperience = sampleWithRequiredData;
        const experienceCollection: IExperience[] = [sampleWithPartialData];
        expectedResult = service.addExperienceToCollectionIfMissing(experienceCollection, experience);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(experience);
      });

      it('should add only unique Experience to an array', () => {
        const experienceArray: IExperience[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const experienceCollection: IExperience[] = [sampleWithRequiredData];
        expectedResult = service.addExperienceToCollectionIfMissing(experienceCollection, ...experienceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const experience: IExperience = sampleWithRequiredData;
        const experience2: IExperience = sampleWithPartialData;
        expectedResult = service.addExperienceToCollectionIfMissing([], experience, experience2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(experience);
        expect(expectedResult).toContain(experience2);
      });

      it('should accept null and undefined values', () => {
        const experience: IExperience = sampleWithRequiredData;
        expectedResult = service.addExperienceToCollectionIfMissing([], null, experience, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(experience);
      });

      it('should return initial array if no Experience is added', () => {
        const experienceCollection: IExperience[] = [sampleWithRequiredData];
        expectedResult = service.addExperienceToCollectionIfMissing(experienceCollection, undefined, null);
        expect(expectedResult).toEqual(experienceCollection);
      });
    });

    describe('compareExperience', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareExperience(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareExperience(entity1, entity2);
        const compareResult2 = service.compareExperience(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareExperience(entity1, entity2);
        const compareResult2 = service.compareExperience(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareExperience(entity1, entity2);
        const compareResult2 = service.compareExperience(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
