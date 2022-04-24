<script>
    export let tab = 1;
    export let value = 1;
    export let label = '';
</script>

<label for={label} class="tab" class:selected={tab == value}>
    <input type="radio" id={label} bind:group={tab} value={value} hidden>
    <slot />
</label>

<style>
    .tab {
        width: 50%;
        height: 36px;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
        border: 2px solid transparent;
    }

    .selected {
        border-radius: 8px;
        background-color: var(--primary-color);
        border: 2px solid var(--dark-color);
    }
</style>