import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IConcours } from '../concours.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../concours.test-samples';

import { ConcoursService, RestConcours } from './concours.service';

const requireRestSample: RestConcours = {
  ...sampleWithRequiredData,
  dateOuverture: sampleWithRequiredData.dateOuverture?.format(DATE_FORMAT),
  dateCloture: sampleWithRequiredData.dateCloture?.format(DATE_FORMAT),
  dateConcours: sampleWithRequiredData.dateConcours?.format(DATE_FORMAT),
};

describe('Concours Service', () => {
  let service: ConcoursService;
  let httpMock: HttpTestingController;
  let expectedResult: IConcours | IConcours[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ConcoursService);
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

    it('should create a Concours', () => {
      const concours = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(concours).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Concours', () => {
      const concours = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(concours).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Concours', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Concours', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Concours', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addConcoursToCollectionIfMissing', () => {
      it('should add a Concours to an empty array', () => {
        const concours: IConcours = sampleWithRequiredData;
        expectedResult = service.addConcoursToCollectionIfMissing([], concours);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(concours);
      });

      it('should not add a Concours to an array that contains it', () => {
        const concours: IConcours = sampleWithRequiredData;
        const concoursCollection: IConcours[] = [
          {
            ...concours,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addConcoursToCollectionIfMissing(concoursCollection, concours);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Concours to an array that doesn't contain it", () => {
        const concours: IConcours = sampleWithRequiredData;
        const concoursCollection: IConcours[] = [sampleWithPartialData];
        expectedResult = service.addConcoursToCollectionIfMissing(concoursCollection, concours);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(concours);
      });

      it('should add only unique Concours to an array', () => {
        const concoursArray: IConcours[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const concoursCollection: IConcours[] = [sampleWithRequiredData];
        expectedResult = service.addConcoursToCollectionIfMissing(concoursCollection, ...concoursArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const concours: IConcours = sampleWithRequiredData;
        const concours2: IConcours = sampleWithPartialData;
        expectedResult = service.addConcoursToCollectionIfMissing([], concours, concours2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(concours);
        expect(expectedResult).toContain(concours2);
      });

      it('should accept null and undefined values', () => {
        const concours: IConcours = sampleWithRequiredData;
        expectedResult = service.addConcoursToCollectionIfMissing([], null, concours, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(concours);
      });

      it('should return initial array if no Concours is added', () => {
        const concoursCollection: IConcours[] = [sampleWithRequiredData];
        expectedResult = service.addConcoursToCollectionIfMissing(concoursCollection, undefined, null);
        expect(expectedResult).toEqual(concoursCollection);
      });
    });

    describe('compareConcours', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareConcours(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareConcours(entity1, entity2);
        const compareResult2 = service.compareConcours(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareConcours(entity1, entity2);
        const compareResult2 = service.compareConcours(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareConcours(entity1, entity2);
        const compareResult2 = service.compareConcours(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
