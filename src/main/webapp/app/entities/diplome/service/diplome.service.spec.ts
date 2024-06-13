import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDiplome } from '../diplome.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../diplome.test-samples';

import { DiplomeService } from './diplome.service';

const requireRestSample: IDiplome = {
  ...sampleWithRequiredData,
};

describe('Diplome Service', () => {
  let service: DiplomeService;
  let httpMock: HttpTestingController;
  let expectedResult: IDiplome | IDiplome[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DiplomeService);
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

    it('should create a Diplome', () => {
      const diplome = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(diplome).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Diplome', () => {
      const diplome = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(diplome).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Diplome', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Diplome', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Diplome', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDiplomeToCollectionIfMissing', () => {
      it('should add a Diplome to an empty array', () => {
        const diplome: IDiplome = sampleWithRequiredData;
        expectedResult = service.addDiplomeToCollectionIfMissing([], diplome);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(diplome);
      });

      it('should not add a Diplome to an array that contains it', () => {
        const diplome: IDiplome = sampleWithRequiredData;
        const diplomeCollection: IDiplome[] = [
          {
            ...diplome,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDiplomeToCollectionIfMissing(diplomeCollection, diplome);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Diplome to an array that doesn't contain it", () => {
        const diplome: IDiplome = sampleWithRequiredData;
        const diplomeCollection: IDiplome[] = [sampleWithPartialData];
        expectedResult = service.addDiplomeToCollectionIfMissing(diplomeCollection, diplome);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(diplome);
      });

      it('should add only unique Diplome to an array', () => {
        const diplomeArray: IDiplome[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const diplomeCollection: IDiplome[] = [sampleWithRequiredData];
        expectedResult = service.addDiplomeToCollectionIfMissing(diplomeCollection, ...diplomeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const diplome: IDiplome = sampleWithRequiredData;
        const diplome2: IDiplome = sampleWithPartialData;
        expectedResult = service.addDiplomeToCollectionIfMissing([], diplome, diplome2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(diplome);
        expect(expectedResult).toContain(diplome2);
      });

      it('should accept null and undefined values', () => {
        const diplome: IDiplome = sampleWithRequiredData;
        expectedResult = service.addDiplomeToCollectionIfMissing([], null, diplome, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(diplome);
      });

      it('should return initial array if no Diplome is added', () => {
        const diplomeCollection: IDiplome[] = [sampleWithRequiredData];
        expectedResult = service.addDiplomeToCollectionIfMissing(diplomeCollection, undefined, null);
        expect(expectedResult).toEqual(diplomeCollection);
      });
    });

    describe('compareDiplome', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDiplome(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDiplome(entity1, entity2);
        const compareResult2 = service.compareDiplome(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDiplome(entity1, entity2);
        const compareResult2 = service.compareDiplome(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDiplome(entity1, entity2);
        const compareResult2 = service.compareDiplome(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
