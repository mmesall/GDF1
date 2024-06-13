import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFormationContinue } from '../formation-continue.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../formation-continue.test-samples';

import { FormationContinueService } from './formation-continue.service';

const requireRestSample: IFormationContinue = {
  ...sampleWithRequiredData,
};

describe('FormationContinue Service', () => {
  let service: FormationContinueService;
  let httpMock: HttpTestingController;
  let expectedResult: IFormationContinue | IFormationContinue[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FormationContinueService);
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

    it('should create a FormationContinue', () => {
      const formationContinue = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(formationContinue).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FormationContinue', () => {
      const formationContinue = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(formationContinue).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FormationContinue', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FormationContinue', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FormationContinue', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFormationContinueToCollectionIfMissing', () => {
      it('should add a FormationContinue to an empty array', () => {
        const formationContinue: IFormationContinue = sampleWithRequiredData;
        expectedResult = service.addFormationContinueToCollectionIfMissing([], formationContinue);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formationContinue);
      });

      it('should not add a FormationContinue to an array that contains it', () => {
        const formationContinue: IFormationContinue = sampleWithRequiredData;
        const formationContinueCollection: IFormationContinue[] = [
          {
            ...formationContinue,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFormationContinueToCollectionIfMissing(formationContinueCollection, formationContinue);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FormationContinue to an array that doesn't contain it", () => {
        const formationContinue: IFormationContinue = sampleWithRequiredData;
        const formationContinueCollection: IFormationContinue[] = [sampleWithPartialData];
        expectedResult = service.addFormationContinueToCollectionIfMissing(formationContinueCollection, formationContinue);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formationContinue);
      });

      it('should add only unique FormationContinue to an array', () => {
        const formationContinueArray: IFormationContinue[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const formationContinueCollection: IFormationContinue[] = [sampleWithRequiredData];
        expectedResult = service.addFormationContinueToCollectionIfMissing(formationContinueCollection, ...formationContinueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const formationContinue: IFormationContinue = sampleWithRequiredData;
        const formationContinue2: IFormationContinue = sampleWithPartialData;
        expectedResult = service.addFormationContinueToCollectionIfMissing([], formationContinue, formationContinue2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formationContinue);
        expect(expectedResult).toContain(formationContinue2);
      });

      it('should accept null and undefined values', () => {
        const formationContinue: IFormationContinue = sampleWithRequiredData;
        expectedResult = service.addFormationContinueToCollectionIfMissing([], null, formationContinue, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formationContinue);
      });

      it('should return initial array if no FormationContinue is added', () => {
        const formationContinueCollection: IFormationContinue[] = [sampleWithRequiredData];
        expectedResult = service.addFormationContinueToCollectionIfMissing(formationContinueCollection, undefined, null);
        expect(expectedResult).toEqual(formationContinueCollection);
      });
    });

    describe('compareFormationContinue', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFormationContinue(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFormationContinue(entity1, entity2);
        const compareResult2 = service.compareFormationContinue(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFormationContinue(entity1, entity2);
        const compareResult2 = service.compareFormationContinue(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFormationContinue(entity1, entity2);
        const compareResult2 = service.compareFormationContinue(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
