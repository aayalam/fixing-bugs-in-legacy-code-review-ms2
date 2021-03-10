package com.assetco.hotspots.optimization;

import com.assetco.hotspots.optimization.*;
import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

import java.math.*;
import java.util.*;

import static com.assetco.search.results.AssetVendorRelationshipLevel.*;
import static com.assetco.search.results.HotspotKey.*;
import static org.junit.jupiter.api.Assertions.*;

public class BugsTests {

    private SearchResults searchResults;

    @Test
    public void precedingPartnerWithLongTrailingAssetsDoesNotWin() {
        final var partnerVendor = makeVendor(Partner);
        final int maximumShowcaseItems = 5;
        var missing = givenAssetInResultsWithVendor(partnerVendor);
        givenAssetInResultsWithVendor(makeVendor(Partner));
        var expected = givenAssetsInResultsWithVendor(maximumShowcaseItems - 1, partnerVendor);

        whenOptimize();

        thenHotspotDoesNotHave(Showcase, missing);
        thenHotspotHasExactly(Showcase, expected);
    }

    private AssetVendor makeVendor(AssetVendorRelationshipLevel relationshipLevel) {
        return new AssetVendor("id", "displayName", relationshipLevel, 0);
    }

    private Asset givenAssetInResultsWithVendor(AssetVendor vendor) {
        AssetPurchaseInfo purchaseInfoLast30Days = new AssetPurchaseInfo(0, 0,
                new Money(BigDecimal.valueOf(0)), new Money(BigDecimal.valueOf(0)));
        AssetPurchaseInfo purchaseInfoLast24Hours = new AssetPurchaseInfo(0, 0,
                new Money(BigDecimal.valueOf(0)), new Money(BigDecimal.valueOf(0)));

        Asset asset = new Asset("id", "title", null, null, purchaseInfoLast30Days, purchaseInfoLast24Hours,
                null, vendor);
        searchResults.addFound(asset);
        return asset;
    }

    private Asset getAsset(AssetVendor vendor) {
        throw new OptimizerTestException();
    }

    private AssetPurchaseInfo getPurchaseInfo() {
        throw new OptimizerTestException();
    }

    private void thenHotspotHasExactly(HotspotKey hotspotKey, List<Asset> expected) {

        Asset[] membersAssets = searchResults.getHotspot(hotspotKey).getMembers().toArray(new Asset[0]);
        Asset[] expectedAssets = expected.toArray(new Asset[0]);

        assertArrayEquals(membersAssets, expectedAssets);

    }

    private ArrayList<Asset> givenAssetsInResultsWithVendor(int count, AssetVendor vendor) {
        var result = new ArrayList<Asset>();
        for (var i = 0; i < count; ++i) {
            result.add(givenAssetInResultsWithVendor(vendor));
        }
        return result;
    }

    private void whenOptimize() {

        SearchResultHotspotOptimizer optimizer = new SearchResultHotspotOptimizer();

        optimizer.optimize(searchResults);


    }

    private void thenHotspotDoesNotHave(HotspotKey key, Asset... forbidden) {

        searchResults.getHotspot(key).getMembers().forEach(asset -> {

            Optional<Asset> searchedAsset = Arrays.stream(forbidden)
                    .filter(forbiddenAsset -> forbiddenAsset.equals(asset))
                    .findAny();

            assertTrue(searchedAsset.isEmpty());

        });

    }

    @BeforeEach
    void setUp() {
        searchResults = new SearchResults();
    }
}
