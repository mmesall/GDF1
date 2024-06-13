import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBailleur } from '../bailleur.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../bailleur.test-samples';

import { BailleurService } from './bailleur.service';

const requireRestSample: IBailleur = {
  ...sampleWithRequiredData,
};

describe('Bailleur Service', () => {
  let service: BailleurService;
  let httpMock: HttpTestingController;
  let expectedResult: IBailleur | IBailleur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BailleurService);
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

    it('should create a Bailleur', () => {
      const bailleur = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(bailleur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Bailleur', () => {
      const bailleur = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(bailleur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Bailleur', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Bailleur', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Bailleur', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBailleurToCollectionIfMissing', () => {
      it('should add a Bailleur to an empty array', () => {
        const bailleur: IBailleur = sampleWithRequiredData;
        expectedResult = service.addBailleurToCollectionIfMissing([], bailleur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bailleur);
      });

      it('should not add a Bailleur to an array that contains it', () => {
        const bailleur: IBailleur = sampleWithRequiredData;
        const bailleurCollection: IBailleur[] = [
          {
            ...bailleur,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBailleurToCollectionIfMissing(bailleurCollection, bailleur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Bailleur to an array that doesn't contain it", () => {
        const bailleur: IBailleur = sampleWithRequiredData;
        const bailleurCollection: IBailleur[] = [sampleWithPartialData];
        expectedResult = service.addBailleurToCollectionIfMissing(bailleurCollection, bailleur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bailleur);
      });

      it('should add only unique Bailleur to an array', () => {
        const bailleurArray: IBailleur[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const bailleurCollection: IBailleur[] = [sampleWithRequiredData];
        expectedResult = service.addBailleurToCollectionIfMissing(bailleurCollection, ...bailleurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bailleur: IBailleur = sampleWithRequiredData;
        const bailleur2: IBailleur = sampleWithPartialData;
        expectedResult = service.addBailleurToCollectionIfMissing([], bailleur, bailleur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bailleur);
        expect(expectedResult).toContain(bailleur2);
      });

      it('should accept null and undefined values', () => {
        const bailleur: IBailleur = sampleWithRequiredData;
        expectedResult = service.addBailleurToCollectionIfMissing([], null, bailleur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bailleur);
      });

      it('should return initial array if no Bailleur is added', () => {
        const bailleurCollection: IBailleur[] = [sampleWithRequiredData];
        expectedResult = service.addBailleurToCollectionIfMissing(bailleurCollection, undefined, null);
        expect(expectedResult).toEqual(bailleurCollection);
      });
    });

    describe('compareBailleur', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBailleur(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBailleur(entity1, entity2);
        const compareResult2 = service.compareBailleur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBailleur(entity1, entity2);
        const compareResult2 = service.compareBailleur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBailleur(entity1, entity2);
        const compareResult2 = service.compareBailleur(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
